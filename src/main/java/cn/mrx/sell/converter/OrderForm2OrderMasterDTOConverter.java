package cn.mrx.sell.converter;

import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.enums.ResultEnum;
import cn.mrx.sell.exception.SellException;
import cn.mrx.sell.form.OrderMarterForm;
import cn.mrx.sell.model.OrderDetail;
import cn.mrx.sell.model.OrderMaster;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mr.X
 * Date：2017/9/8 20:54
 * Description：
 */
public class OrderForm2OrderMasterDTOConverter {

    public static OrderMasterDTO convert(OrderMarterForm orderForm) {
        Gson gson = new Gson();
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        orderMasterDTO.setBuyerName(orderForm.getName());
        orderMasterDTO.setBuyerPhone(orderForm.getPhone());
        orderMasterDTO.setBuyerAddress(orderForm.getAddress());
        orderMasterDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderMasterDTO.setOrderDetailList(orderDetailList);
        return orderMasterDTO;
    }
}
