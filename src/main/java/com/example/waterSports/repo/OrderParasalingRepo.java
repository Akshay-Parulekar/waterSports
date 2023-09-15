package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderParasailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderParasalingRepo extends JpaRepository<OrderParasailing, Long>
{
    List<OrderParasailing> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Long findTopByOrderByBillNoDesc();
}
