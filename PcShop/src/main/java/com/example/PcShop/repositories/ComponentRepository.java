package com.example.PcShop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PcShop.entities.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findByCategory(String category);
}
