package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderWaterSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderWaterSportRepo extends JpaRepository<OrderWaterSport, Long>
{
    List<OrderWaterSport> findByDateBetweenOrderByBillNoDesc(LocalDate startDate, LocalDate endDate);
    OrderWaterSport findTopByOrderByBillNoDesc();
    OrderWaterSport findByBillNo(Long billNo);

    @Query("select count(id) from OrderWaterSport where idRef = :idRef")
    Integer countReferences(Long idRef);
}
