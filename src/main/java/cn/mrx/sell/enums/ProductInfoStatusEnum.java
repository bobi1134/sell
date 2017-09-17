package cn.mrx.sell.enums;

import lombok.Getter;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 22:02
 * Description:
 */
@Getter
public enum ProductInfoStatusEnum {

    UP(0, "在架"), DOWN(1, "下架");

    private Integer code;
    private String message;

    ProductInfoStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
