package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order,Long> {
//    Order save(Order order);
    List<Order> findByDeliveryZip(String deliveryZip);
}
