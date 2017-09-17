package cn.mrx.sell.service;

import cn.mrx.sell.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 11:34
 * Description:
 */
public interface OrderMasterService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderMasterDTO create(OrderMasterDTO orderDTO);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    OrderMasterDTO findOne(String orderId);

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMasterDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    OrderMasterDTO cancel(OrderMasterDTO orderDTO);

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    OrderMasterDTO finish(OrderMasterDTO orderDTO);

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    OrderMasterDTO paid(OrderMasterDTO orderDTO);
}
