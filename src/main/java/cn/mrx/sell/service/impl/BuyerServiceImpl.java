package cn.mrx.sell.service.impl;

import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.enums.ResultEnum;
import cn.mrx.sell.exception.SellException;
import cn.mrx.sell.model.OrderMaster;
import cn.mrx.sell.service.BuyerService;
import cn.mrx.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author：Mr.X
 * Date：2017/9/8 20:51
 * Description：
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderMasterDTO findOrderMasterOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderMasterDTO cancelOrderMaster(String openid, String orderId) {
        OrderMasterDTO orderMasterDTO = checkOrderOwner(openid, orderId);
        if (orderMasterDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderMasterService.cancel(orderMasterDTO);
    }

    /**
     * 判断该订单是否属于该用户
     * @param openid
     * @param orderId
     * @return
     */
    private OrderMasterDTO checkOrderOwner(String openid, String orderId) {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        if (orderMasterDTO == null) return null;
        //判断是否是自己的订单
        if (!orderMasterDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderMasterDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderMasterDTO;
    }
}
