package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.project.dto.BeerItemEdit;

@Entity
@Table(name = "beer_sales")
public class BeerSaleEdit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private Integer quantity;
    private Integer beerId;


  private transient BeerItemEdit beer;  // ← DBに影響しない表示用データ




      public void setId(Integer id) {
        this.id = id;
    }

 public void setDate(LocalDate date) {
        this.date = date;
    }


public void setQuantity(Integer quantity) {
    this.quantity = quantity;
}

public void setBeerId(Integer id) {
    this.beerId = id;
}
 public void setBeer(BeerItemEdit beer) {
        this.beer = beer;
    }




public Integer getId() {
        return id;
    }

public Integer getQuantity() {
    return this.quantity;
}

public Integer getBeerId() {
    return beerId;
}

 public BeerItemEdit getBeer() {
        return beer;
    }


}