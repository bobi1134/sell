package cn.mrx.sell.service.impl;

import cn.mrx.sell.dto.OrderMasterDTO;
import cn.mrx.sell.model.OrderDetail;
import cn.mrx.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Author：Mr.X
 * Date：2017/9/8 16:36
 * Description：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterServiceImpl service;

    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID = "1504862443928867428";

    @Test
    public void create() throws Exception {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        /** 订单基本信息 */
        orderMasterDTO.setBuyerName("邓鸿飞");
        orderMasterDTO.setBuyerAddress("三区2舍");
        orderMasterDTO.setBuyerPhone("13222222222");
        orderMasterDTO.setBuyerOpenid(BUYER_OPENID);

        /** 订单明细信息 */
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(10);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(20);
        orderDetailList.add(o2);

        orderMasterDTO.setOrderDetailList(orderDetailList);
        OrderMasterDTO result = service.create(orderMasterDTO);
        log.info("【创建订单】result={}", result);
    }

    @Test
    public void findOne() throws Exception {
        OrderMasterDTO result = service.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}", result);
    }

    @Test
    public void findList() throws Exception {
        PageRequest request = new PageRequest(0,2);
        Page<OrderMasterDTO> orderDTOPage = service.findList(BUYER_OPENID, request);
        System.out.println(orderDTOPage);
        System.out.println(orderDTOPage.getContent());
    }

    @Test
    public void cancel() throws Exception {
        OrderMasterDTO orderMasterDTO = service.findOne(ORDER_ID);
        service.cancel(orderMasterDTO);
    }

    @Test
    public void finish() throws Exception {
        OrderMasterDTO orderDTO = service.findOne(ORDER_ID);
        service.finish(orderDTO);
    }

    @Test
    public void paid() throws Exception {
        OrderMasterDTO orderMasterDTO= service.findOne(ORDER_ID);
        service.paid(orderMasterDTO);
    }
}