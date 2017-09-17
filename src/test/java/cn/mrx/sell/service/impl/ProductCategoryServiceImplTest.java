package cn.mrx.sell.service.impl;

import cn.mrx.sell.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 20:46
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl service;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = service.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> productCategoryList = service.findAll();
        System.out.println(productCategoryList);
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> list = service.findByCategoryTypeIn(Arrays.asList(1,2));
        System.out.println(list);
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("男生专享", 3);
        service.save(productCategory);
    }

}