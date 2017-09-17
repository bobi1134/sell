package cn.mrx.sell.repository;

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
 * Date: 2017/9/6 15:45
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOne() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("男生最爱", 1);
        repository.save(productCategory);

        productCategory = new ProductCategory("女生最爱", 2);
        repository.save(productCategory);
    }

    @Test
    public void update() {
        ProductCategory productCategory = repository.findOne(1);
        productCategory.setCategoryName("男生最爱");
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> list = repository.findByCategoryTypeIn(Arrays.asList(1,2));
        System.out.println(list);
    }
}