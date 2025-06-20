// package com.example.project.entity;

// import jakarta.persistence.*;
// import java.time.LocalDate;

// @Entity
// @Table(name = "beer_sales")
// public class BeerSale {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     private LocalDate date;

//     @Column(name = "beer_id")
//     private Integer beerId;

//     private Integer quantity;

//     @Column(name = "enter_user_id")
//     private Integer enterUserId;

//     // getter・setter省略可（後で追加してもOK）

//  public void setDate(LocalDate date) {
//         this.date = date;
//     }

// public void setBeerId(Integer beerId) {
//     this.beerId = beerId;
// }

// public void setQuantity(Integer quantity) {
//     this.quantity = quantity;
// }

// public void setEnterUserId(Integer enterUserId) {
//     this.enterUserId = enterUserId;
// }


// }