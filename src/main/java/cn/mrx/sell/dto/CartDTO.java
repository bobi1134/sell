package cn.mrx.sell.dto;

import lombok.Data;

/**
 * Author：Mr.X
 * Date：2017/9/8 16:23
 * Description：
 */
@Data
public class CartDTO {

    /** 商品Id. */
    private String productId;

    /** 数量. */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
