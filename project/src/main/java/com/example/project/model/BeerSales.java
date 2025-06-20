// package com.example.project.model;

// import jakarta.persistence.*;
// import java.time.LocalDate;

// @Entity
// @Table(name = "beer_sales")
// public class BeerSales {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private LocalDate date;

//     @Column(name = "beer_id")
//     private Long beerId;

//     private int quantity;


//     @Column(name = "user_id")
//     private Long userId;

//     // ===== Getter & Setter =====

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public LocalDate getDate() {
//         return date;
//     }

//     public void setDate(LocalDate date) {
//         this.date = date;
//     }

//     public Long getBeerId() {
//         return beerId;
//     }

//     public void setBeerId(Long beerId) {
//         this.beerId = beerId;
//     }

//     public int getQuantity() {
//         return quantity;
//     }

//     public void setQuantity(int quantity) {
//         this.quantity = quantity;
//     }
    
//     public Long getUserId() {
//         return userId;
//     }

//     public void setUserId(Long userId) {
//         this.userId = userId;
//     }
// }
