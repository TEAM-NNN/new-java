package com.example.project.service;

import com.example.project.entity.Beer;
import com.example.project.entity.BeerSaleEdit;
import com.example.project.model.User;
import com.example.project.model.BeerSalesDetailDTO;
import com.example.project.repository.BeerRepository;
import com.example.project.repository.BeerSalesRepository;
import com.example.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DetailService {

    private final BeerSalesRepository beerSalesRepository;
    private final BeerRepository beerRepository;
    private final UserRepository userRepository;

    public DetailService(BeerSalesRepository beerSalesRepository,
                         BeerRepository beerRepository,
                         UserRepository userRepository) {
        this.beerSalesRepository = beerSalesRepository;
        this.beerRepository = beerRepository;
        this.userRepository = userRepository;
    }

    public List<BeerSalesDetailDTO> getDetailsByDate(LocalDate date) {
        List<BeerSaleEdit> salesList = beerSalesRepository.findByDate(date);

        // 🔸 ビール情報取得 (id → Beer)
        Map<Long, Beer> beerMap = beerRepository.findAll().stream()
                .filter(b -> b.getId() != null)
                .collect(Collectors.toMap(
                        Beer::getId,
                        Function.identity(),
                        (a, b) -> a  // 重複があれば先を採用
                ));

        // 🔸 ユーザー情報取得 (id → User)
        Map<Long, String> userEmailMap = userRepository.findAll().stream()
                .filter(u -> u.getId() != null && u.getEmail() != null)
                .collect(Collectors.toMap(
                        User::getId,
                        User::getEmail,
                        (a, b) -> a
                ));

        List<BeerSalesDetailDTO> details = new ArrayList<>();

        for (BeerSaleEdit sale : salesList) {
            Beer beer = beerMap.get(sale.getBeerId());
            if (beer != null) {
                String name = beer.getName();
                int quantity = sale.getQuantity();
                int price = beer.getPrice() != null ? beer.getPrice() : 0;
                int total = quantity * price;
                Long janCode = beer.getJanCode();

                // 🔹 入力者メール取得
                String email = userEmailMap.getOrDefault(sale.getUserId(), "不明");

                details.add(new BeerSalesDetailDTO(name, quantity, total, janCode, email));
            }
        }

        return details;
    }
}
