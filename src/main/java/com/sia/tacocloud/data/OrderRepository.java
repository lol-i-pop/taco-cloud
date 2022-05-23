package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Order;


public interface OrderRepository {
    Order save(Order order);
}
