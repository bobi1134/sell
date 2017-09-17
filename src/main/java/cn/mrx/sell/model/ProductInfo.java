package cn.mrx.sell.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 21:43
 * Description:
 */
@Entity
@Data
public class ProductInfo {

    /** 商品id. */
    @Id
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus;

    /** 商品类目编号.(关联ProductCategory的categoryType字段) */
    private Integer categoryType;
}
