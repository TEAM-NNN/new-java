package com.example.project.dto;

public class BeerItemEdit {
    private int no;
    private String name;
    private int price;
    private Long janCode;
    private Integer soldCount;

    // ★ デフォルトコンストラクタ（これが必要）
    public BeerItemEdit() {
    }

    // ★ 引数ありコンストラクタ（1つだけ）
    public BeerItemEdit(int no, String name, int price, Long janCode) {
        this.no = no;
        this.name = name;
        this.price = price;
        this.janCode = janCode;
        this.soldCount = 0;
    }

    // ★ Getter & Setter
    public int getNo() { return no; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public Long getJanCode() { return janCode; }
    public Integer getSoldCount() { return soldCount; }
    public void setSoldCount(Integer soldCount) { this.soldCount = soldCount; }
    public void setNo(int no) {
    this.no = no;
}

}
