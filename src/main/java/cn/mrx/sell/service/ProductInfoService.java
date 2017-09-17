package cn.mrx.sell.service;

import cn.mrx.sell.dto.CartDTO;
import cn.mrx.sell.model.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 21:54
 * Description:
 */
public interface ProductInfoService {

    /**
     * 根据商品id查询商品信息
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品信息
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有商品信息
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品信息
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
