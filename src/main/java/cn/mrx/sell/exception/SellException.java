package cn.mrx.sell.exception;

import cn.mrx.sell.enums.ResultEnum;

/**
 * Author：Mr.X
 * Date：2017/9/8 15:34
 * Description：
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
