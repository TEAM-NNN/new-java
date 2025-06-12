package com.example.project.dto;

import lombok.Data;

@Data
public class ForecastItem {
    private String beerName;
    private Integer price;
    private Integer predictedQuantity;

    public int getTotal() {
        return price * predictedQuantity;
    }
}
