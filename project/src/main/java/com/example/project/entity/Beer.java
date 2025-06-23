package com.example.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "beers")
public class Beer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;  // ビール名（例：IPA）
    private Integer price; // 価格（例：900円）
    public Long janCode;
    public Integer getPrice(){
        return price;
    }
    public Long getJanCode() { return janCode; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void seId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(Integer price) { this.price = price; }
    public void setJanCode(Long janCode) { this.janCode = janCode; }
    }
