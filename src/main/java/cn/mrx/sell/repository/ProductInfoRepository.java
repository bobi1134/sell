package cn.mrx.sell.repository;

import cn.mrx.sell.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: xialiangbo
 * Date: 2017/9/6 21:46
 * Description:
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
