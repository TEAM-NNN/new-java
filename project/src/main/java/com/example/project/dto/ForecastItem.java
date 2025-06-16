package com.example.project.dto;

import lombok.Data;

@Data
public class ForecastItem {
    private String beerName;
    private Integer price;
    private Integer predictedQuantity;

     public ForecastItem(String beerName, Integer price, Integer predictedQuantity) {
        this.beerName = beerName;
        this.price = price;
        this.predictedQuantity = predictedQuantity;
    }

    public int getTotal() {
        return price * predictedQuantity;
    }
}
