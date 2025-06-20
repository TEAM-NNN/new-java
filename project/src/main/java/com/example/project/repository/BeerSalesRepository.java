package com.example.project.repository;

import com.example.project.entity.BeerSaleEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BeerSalesRepository extends JpaRepository<BeerSaleEdit, Long> {

    List<BeerSaleEdit> findByDate(LocalDate date);

    List<BeerSaleEdit> findByDateAndBeerId(LocalDate date, Long beerId);

    List<BeerSaleEdit> findByDateBetween(LocalDate start, LocalDate end);
}
