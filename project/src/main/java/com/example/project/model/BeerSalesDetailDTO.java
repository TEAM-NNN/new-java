//実績詳細表示用

package com.example.project.model;

public class BeerSalesDetailDTO {

    private String beerName;
    private int quantity;
    private int totalSales;

    public BeerSalesDetailDTO(String beerName, int quantity, int totalSales){

        this.beerName = beerName;
        this.quantity = quantity;
        this.totalSales = totalSales;

    }

    public String getBeerName(){

        return beerName;

    }

    public int getQuantity(){

        return quantity;

    }

    public int getTotalSales(){

        return totalSales;

    }


    
}
