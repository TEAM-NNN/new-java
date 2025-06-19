package com.example.project.service;

import com.example.project.model.*;
import com.example.project.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DetailService {

    private final BeerSalesRepository beerSalesRepository;
    private final BeerRepository beerRepository;

    public DetailService(BeerSalesRepository beerSalesRepository, BeerRepository beerRepository) {
        this.beerSalesRepository = beerSalesRepository;
        this.beerRepository = beerRepository;

    }

    public List<BeerSalesDetailDTO> getDetailsByDate(LocalDate date) {
        List<BeerSales> salesList = beerSalesRepository.findByDate(date);

        // ビールID → Beer オブジェクト
        Map<Long, Beer> beerMap = beerRepository.findAll().stream()
                .collect(Collectors.toMap(Beer::getId, beer -> beer));

        // ビール名 → JANコード
        Map<String, String> janMap = Map.of(
            "ホワイトビール", "4901234567894",
            "ラガー", "4512345678907",
            "ペールエール", "4987654321097",
            "フルーツビール", "4545678901234",
            "黒ビール", "4999999999996",
            "IPA", "4571234567892"
        );

        List<BeerSalesDetailDTO> details = new ArrayList<>();

        for (BeerSales sale : salesList) {
            Beer beer = beerMap.get(sale.getBeerId());
            if (beer != null) {
                String name = beer.getName();
                int quantity = sale.getQuantity();
                int price = beer.getPrice();
                int total = quantity * price;
                String jan = janMap.getOrDefault(name, "不明");

                details.add(new BeerSalesDetailDTO(name, quantity, total, jan));
            }
        }

        return details;
    }
}