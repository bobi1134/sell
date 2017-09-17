package cn.mrx.sell.vo;

import lombok.Data;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 22:45
 * Description:
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
