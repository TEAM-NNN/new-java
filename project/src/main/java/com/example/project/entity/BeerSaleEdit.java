package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.project.dto.BeerItemEdit;

@Entity
@Table(name = "beer_sales")
public class BeerSaleEdit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String email;

    @Column(name = "beer_id")
    private Long beerId;

    private int quantity;

    @Column(name = "enter_user_id")
    private Long userId;

    // 表示用データ（DBには保存されない）
    @Transient
    private BeerItemEdit beer;

    // private String username;

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    //   public String getUsername() {
    //     return username;
    //   }

    public String getEmail(){
        return email;
    }

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BeerItemEdit getBeer() {
        return beer;
    }

    public void setBeer(BeerItemEdit beer) {
        this.beer = beer;
    }
}
