package com.example.PcShop.repositories;

import com.example.PcShop.entities.Order;
import com.example.PcShop.entities.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Integer> {
}
