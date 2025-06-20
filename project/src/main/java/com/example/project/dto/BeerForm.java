package com.example.project.dto;

import java.util.List;

public class BeerForm {
    private List<BeerItem> beerList;

    public List<BeerItem> getBeerList() {
        return beerList;
    }

    public void setBeerList(List<BeerItem> beerList) {
        this.beerList = beerList;
    }
}

