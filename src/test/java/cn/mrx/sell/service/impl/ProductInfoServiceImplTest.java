package cn.mrx.sell.service.impl;

import cn.mrx.sell.enums.ProductInfoStatusEnum;
import cn.mrx.sell.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 22:17
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = service.findOne("123456");
        System.out.println(productInfo);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> list = service.findUpAll();
        System.out.println(list);
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> page = service.findAll(pageRequest);
        System.out.println(page.getContent());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://def.jpg");
        productInfo.setProductStatus(ProductInfoStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        service.save(productInfo);
    }

}