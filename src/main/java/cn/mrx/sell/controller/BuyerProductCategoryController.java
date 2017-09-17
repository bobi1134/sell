package cn.mrx.sell.controller;

import cn.mrx.sell.model.ProductCategory;
import cn.mrx.sell.model.ProductInfo;
import cn.mrx.sell.service.ProductCategoryService;
import cn.mrx.sell.service.ProductInfoService;
import cn.mrx.sell.utils.ResultVOUtil;
import cn.mrx.sell.vo.ProductCategoryVO;
import cn.mrx.sell.vo.ProductInfoVO;
import cn.mrx.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 22:35
 * Description:
 */
@RestController
@RequestMapping("/buyer/category")
public class BuyerProductCategoryController {

    @Autowired
    private ProductInfoService infoService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
        // 1. 查询所有的上架商品
        List<ProductInfo> productInfoList = infoService.findUpAll();

        // 2. 查询类别(一次性查询)
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 3. 拼接数据
        List<ProductCategoryVO> categoryVOList = new ArrayList<>();
        for (ProductCategory category : productCategoryList) {
            ProductCategoryVO categoryVO = new ProductCategoryVO();
            categoryVO.setCategoryName(category.getCategoryName());
            categoryVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> infoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    infoVOList.add(productInfoVO);
                }
            }

            categoryVO.setProductInfoVOList(infoVOList);
            categoryVOList.add(categoryVO);
        }
        return ResultVOUtil.success(categoryVOList);
    }
}
