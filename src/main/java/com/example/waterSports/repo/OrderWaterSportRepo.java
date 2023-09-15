package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderWaterSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderWaterSportRepo extends JpaRepository<OrderWaterSport, Long>
{
    List<OrderWaterSport> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
