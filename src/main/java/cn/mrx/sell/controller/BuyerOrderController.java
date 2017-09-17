package cn.mrx.sell.controller;

import cn.mrx.sell.converter.OrderForm2OrderMasterDTOConverter;
import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.enums.ResultEnum;
import cn.mrx.sell.exception.SellException;
import cn.mrx.sell.form.OrderMarterForm;
import cn.mrx.sell.service.BuyerService;
import cn.mrx.sell.service.OrderMasterService;
import cn.mrx.sell.utils.ResultVOUtil;
import cn.mrx.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：Mr.X
 * Date：2017/9/8 20:49
 * Description：
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderMarterForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderMasterDTO orderDTO = OrderForm2OrderMasterDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderMasterDTO createResult = orderMasterService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderMasterDTO>> list(@RequestParam("openid") String openid,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderMasterDTO> orderDTOPage = orderMasterService.findList(openid, request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /** 订单详情 */
    @GetMapping("/detail")
    public ResultVO<OrderMasterDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        OrderMasterDTO orderMasterDTO = buyerService.findOrderMasterOne(openid, orderId);
        return ResultVOUtil.success(orderMasterDTO);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        buyerService.cancelOrderMaster(openid, orderId);
        return ResultVOUtil.success();
    }
}
