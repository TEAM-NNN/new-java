package com.example.project.dto;

import lombok.Data;

@Data
public class ForecastItem {
    private String beerName;
    private Integer price;
    private Integer quantity;

     public ForecastItem(String beerName, Integer price, Integer predictedQuantity) {
        this.beerName = beerName;
        this.price = price;
        this.quantity = predictedQuantity;
    }

    public int getTotal() {
    if (price == null || quantity == null) {
        return 0;
    }
    return price * quantity;
    }


}
