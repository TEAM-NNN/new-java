package com.example.project.repository;

import com.example.project.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    // 🔧 このメソッドを追加してください
    List<Weather> findByDateBetween(LocalDate start, LocalDate end);
}
