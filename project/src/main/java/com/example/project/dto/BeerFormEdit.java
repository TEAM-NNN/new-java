package com.example.project.dto;

import java.util.List;

public class BeerFormEdit {
    private List<BeerItemEdit> beerList;

    public List<BeerItemEdit> getBeerList() {
        return beerList;
    }

    public void setBeerList(List<BeerItemEdit> beerList) {
        this.beerList = beerList;
    }
}
