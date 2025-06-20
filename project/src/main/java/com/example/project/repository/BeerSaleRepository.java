package com.example.project.repository;

import com.example.project.entity.BeerSale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BeerSaleRepository extends JpaRepository<BeerSale, Integer> {
    boolean existsByDate(LocalDate date);
    List<BeerSale> findByDate(LocalDate date);
}

