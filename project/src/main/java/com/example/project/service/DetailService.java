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

        // 取得所有啤酒名称，用 map 快速查找
        Map<Long, String> beerNameMap = beerRepository.findAll().stream()
                .collect(Collectors.toMap(Beer::getId, Beer::getName));

        List<BeerSalesDetailDTO> details = new ArrayList<>();
        for (BeerSales sale : salesList) {
            String beerName = beerNameMap.getOrDefault(sale.getBeerId(), "不明");
            int quantity = sale.getQuantity();
            int amount = quantity * sale.getEnter();
            details.add(new BeerSalesDetailDTO(beerName, quantity, amount));
        }

        return details;
    }
}
