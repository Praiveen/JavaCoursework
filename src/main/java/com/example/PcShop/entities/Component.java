package com.example.PcShop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity
public class Component {

    @Id
    private int id;
    private String category;
    private String name;
    private float componentPrice;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "component")
    private List<OrderList> OrderList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getComponentPrice() {
        return componentPrice;
    }

    public void setComponentPrice(float componentPrice) {
        this.componentPrice = componentPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
