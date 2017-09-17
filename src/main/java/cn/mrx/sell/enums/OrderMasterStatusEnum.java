package cn.mrx.sell.enums;


import lombok.Getter;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 10:48
 * Description:
 */
@Getter
public enum OrderMasterStatusEnum {

    NEW(0, "新订单"), FINISHED(1, "完结"), CANCEL(2, "已取消");

    private Integer code;
    private String message;

    OrderMasterStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
