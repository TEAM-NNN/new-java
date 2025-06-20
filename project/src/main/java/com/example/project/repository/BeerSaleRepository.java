package com.example.project.repository;

import com.example.project.entity.BeerSaleEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BeerSaleRepository extends JpaRepository<BeerSaleEdit, Integer> {
    boolean existsByDate(LocalDate date);
    List<BeerSaleEdit> findByDate(LocalDate date);
}

