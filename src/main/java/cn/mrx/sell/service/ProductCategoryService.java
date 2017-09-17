package cn.mrx.sell.service;

import cn.mrx.sell.model.ProductCategory;

import java.util.List;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 20:42
 * Description:
 */
public interface ProductCategoryService {

    /**
     * 根据商品类别id查询商品类别
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有的商品类别
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据品类别id集合查询商品类别
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 保存商品类别
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
