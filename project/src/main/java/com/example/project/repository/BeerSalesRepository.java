package com.example.project.repository;

import com.example.project.model.BeerSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BeerSalesRepository extends JpaRepository<BeerSales, Long> {

    // ① 某一天所有销售记录（用于明细页）
    List<BeerSales> findByDate(LocalDate date);

    // ② 某一天所有销售记录（+ 啤酒ID）
    List<BeerSales> findByDateAndBeerId(LocalDate date, Long beerId);

    // ③ 某月销售记录（用于整月销售聚合）
    List<BeerSales> findByDateBetween(LocalDate start, LocalDate end);
}
