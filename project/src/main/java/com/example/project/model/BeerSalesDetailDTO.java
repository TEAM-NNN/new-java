//実績詳細表示用

package com.example.project.model;

public class BeerSalesDetailDTO {

    private String beerName;     // ビール名
    private int quantity;        // 販売本数
    private int totalSales;      // 売上金額
    private Long janCode;      // JANコード

    // 全項目を受け取るコンストラクタ
    public BeerSalesDetailDTO(String beerName, int quantity, int totalSales, Long janCode) {
        this.beerName = beerName;
        this.quantity = quantity;
        this.totalSales = totalSales;
        this.janCode = janCode;
    }

    // Getter
    public String getBeerName() {
        return beerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public Long getJanCode() {
        return janCode;
    }
}
