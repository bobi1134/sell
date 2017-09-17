package cn.mrx.sell.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 11:09
 * Description:
 */
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    /** 订单id. 关联OrderMaster的orderId字段 */
    private String orderId;

    /** 商品id. 关联ProductInfo的productId字段 */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;
}
