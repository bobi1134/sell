package cn.mrx.sell.repository;

import cn.mrx.sell.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: xialiangbo
 * Date: 2017/9/7 11:12
 * Description:
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
