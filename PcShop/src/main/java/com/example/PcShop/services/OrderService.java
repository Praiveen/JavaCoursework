package com.example.PcShop.services;

import com.example.PcShop.entities.Component;
import com.example.PcShop.entities.OrderList;
import com.example.PcShop.entities.User;
import com.example.PcShop.repositories.OrderListRepository;
import com.example.PcShop.repositories.OrderRepository;
import com.example.PcShop.entities.Order;
import com.example.PcShop.repositories.RoleRepository;
import com.example.PcShop.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
