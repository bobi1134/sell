package cn.mrx.sell.repository;

import cn.mrx.sell.model.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 11:14
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "110110";

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("罗泽东");
        orderMaster.setBuyerPhone("13222222222");
        orderMaster.setBuyerAddress("三区二十二舍");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        repository.save(orderMaster);
    }

    @Test
    public void update() {
        OrderMaster orderMaster = repository.findOne("1234567");
        orderMaster.setBuyerName("罗泽东");
        orderMaster.setBuyerAddress("三区二十二舍");
        repository.save(orderMaster);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<OrderMaster> page = repository.findByBuyerOpenid(OPENID, pageRequest);
        System.out.println(page.getContent());
    }

}