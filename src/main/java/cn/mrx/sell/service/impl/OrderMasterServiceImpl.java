package cn.mrx.sell.service.impl;

import cn.mrx.sell.converter.OrderMaster2OrderMasterDTOConverter;
import cn.mrx.sell.dto.CartDTO;
import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.enums.OrderMasterStatusEnum;
import cn.mrx.sell.enums.PayStatusEnum;
import cn.mrx.sell.enums.ResultEnum;
import cn.mrx.sell.exception.SellException;
import cn.mrx.sell.model.OrderDetail;
import cn.mrx.sell.model.OrderMaster;
import cn.mrx.sell.model.ProductInfo;
import cn.mrx.sell.repository.OrderDetailRepository;
import cn.mrx.sell.repository.OrderMasterRepository;
import cn.mrx.sell.service.OrderMasterService;
import cn.mrx.sell.service.ProductInfoService;
import cn.mrx.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 11:43
 * Description:
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    @Transactional
    public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        /** 1、写入订单表orderMaster */
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster); /** buyerName,buyerPhone,buyerAddress,buyerOpenid */
        orderMasterDTO.setOrderId(orderId);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderMasterStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        /** 2、写入订单明细表：一个订单包含多个订单明细(订一份午餐，里面包含辣子鸡丁、荷包饭、青椒肉丝等三个人的量) */
        for(OrderDetail orderDetail : orderMasterDTO.getOrderDetailList()){
            ProductInfo productInfo =  productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            /** 计算订单总价(单价*数量) */
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            /** 订单明细入库 */
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail); /** productId,productName,productIcon */
            orderDetailRepository.save(orderDetail);
        }

        /** 3、扣库存 */
        List<CartDTO> cartDTOList = orderMasterDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) throw new SellException(ResultEnum.ORDER_NOT_EXIST);

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        orderMasterDTO.setOrderDetailList(orderDetailList);
        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMasterDTO> orderDTOList = OrderMaster2OrderMasterDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderMasterDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {

        OrderMaster orderMaster = new OrderMaster();

        /** 判断订单状态 */
        if (!orderMasterDTO.getOrderStatus().equals(OrderMasterStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /** 修改订单状态为已取消 */
        orderMasterDTO.setOrderStatus(OrderMasterStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        /** 返回库存 */
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderMasterDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderMasterDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        /** 如果已支付, 需要退款 */
        if (orderMasterDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return orderMasterDTO;
    }

    @Override
    @Transactional
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {
        /** 判断订单状态 */
        if (!orderMasterDTO.getOrderStatus().equals(OrderMasterStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /** 修改订单状态 */
        orderMasterDTO.setOrderStatus(OrderMasterStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderMasterDTO;
    }

    @Override
    @Transactional
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {
        /** 判断订单状态 */
        if (!orderMasterDTO.getOrderStatus().equals(OrderMasterStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /** 判断支付状态 */
        if (!orderMasterDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderMasterDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        /** 修改支付状态 */
        orderMasterDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMasterDTO.setOrderStatus(OrderMasterStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderMasterDTO;
    }
}
