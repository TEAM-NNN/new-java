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

    public Integer getPrice(){
        return price;
    }
}

