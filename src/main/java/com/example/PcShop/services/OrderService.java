package com.example.PcShop.services;

import com.example.PcShop.entities.OrderList;
import com.example.PcShop.repositories.OrderListRepository;
import com.example.PcShop.repositories.OrderRepository;
import com.example.PcShop.entities.Order;
import org.springframework.stereotype.Service;


@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderListRepository orderListRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderListRepository orderListRepository) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
    }


    public void saveOrderList(OrderList orderList) {
        orderListRepository.save(orderList);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrderList(int deleteStorageItemId) {
        orderListRepository.deleteById(deleteStorageItemId);
    }

    public OrderList findById(int id){
        return orderListRepository.findById(id).orElse(null);
    }
}
