package com.sia.tacocloud.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sia.tacocloud.pojo.Order;
import com.sia.tacocloud.pojo.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    public JdbcOrderRepository(JdbcTemplate jdbc){
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("taco_order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("taco_order_tacos");
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for(Taco taco : tacos){

            saveTacoToOrder(taco,orderId);
        }
        return order;
    }


    private long saveOrderDetails(Order order) {
        Map<String ,Object> values = objectMapper.convertValue(order,Map.class);
        values.put("placedAt",order.getPlacedAt());
        long orderId = orderInserter
                .executeAndReturnKey(values) //execute 接受一个map ,key为要插入的字段名，value为对应的值
                .longValue();
        return orderId;
    }
    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String,Object> values = new HashMap<>();
        values.put("tacoOrder",orderId);
        values.put("taco",taco.getId());
        orderTacoInserter.execute(values);
    }
}
