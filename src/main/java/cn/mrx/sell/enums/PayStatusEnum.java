package cn.mrx.sell.enums;

import lombok.Getter;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 10:50
 * Description:
 */
@Getter
public enum PayStatusEnum {

    WAIT(0, "等待支付"), SUCCESS(1, "支付成功");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
