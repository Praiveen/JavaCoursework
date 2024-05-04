package com.example.PcShop.services;

import com.example.PcShop.repositories.ComponentRepository;
import lombok.AllArgsConstructor;
import com.example.PcShop.entities.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComponentService {
    private ComponentRepository componentRepository;

    public List<Component> findAll() {
        return componentRepository.findAll();
    }

    public List<Component> findByCategory(String category) {
        return componentRepository.findByCategory(category);

    }

    public Component findById(int id){
        return componentRepository.findById(id).orElse(null);
    }
}

