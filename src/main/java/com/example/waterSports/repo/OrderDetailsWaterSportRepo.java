package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderDetailsWaterSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsWaterSportRepo extends JpaRepository<OrderDetailsWaterSport, Long>
{
    List<OrderDetailsWaterSport> findByBillNo(Long billNo);
}
