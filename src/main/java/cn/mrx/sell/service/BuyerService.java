package cn.mrx.sell.service;

import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.model.OrderMaster;

/**
 * Author：Mr.X
 * Date：2017/9/8 20:50
 * Description：
 */
public interface BuyerService {

    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderMasterDTO findOrderMasterOne(String openid, String orderId);

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderMasterDTO cancelOrderMaster(String openid, String orderId);
}
