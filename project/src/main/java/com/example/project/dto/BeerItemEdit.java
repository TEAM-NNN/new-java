package com.example.project.dto;

public class BeerItemEdit {
    private int no;
    private String name;
    private int price;
    private String jan;
    private Integer soldCount;

    // ★ デフォルトコンストラクタ（これが必要）
    public BeerItemEdit() {
    }

    // ★ 引数ありコンストラクタ（1つだけ）
    public BeerItemEdit(int no, String name, int price, String jan) {
        this.no = no;
        this.name = name;
        this.price = price;
        this.jan = jan;
        this.soldCount = 0;
    }

    // ★ Getter & Setter
    public int getNo() { return no; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getJan() { return jan; }
    public Integer getSoldCount() { return soldCount; }
    public void setSoldCount(Integer soldCount) { this.soldCount = soldCount; }
    public void setNo(int no) {
    this.no = no;
}

}
