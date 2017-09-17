package cn.mrx.sell.converter;

import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.model.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author：Mr.X
 * Date：2017/9/8 17:35
 * Description：
 */
public class OrderMaster2OrderMasterDTOConverter {

    public static OrderMasterDTO convert(OrderMaster orderMaster) {
        OrderMasterDTO orderDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderMasterDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
