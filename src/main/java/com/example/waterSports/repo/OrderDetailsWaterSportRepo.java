package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderDetailsWaterSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderDetailsWaterSportRepo extends JpaRepository<OrderDetailsWaterSport, Long>
{
    List<OrderDetailsWaterSport> findByBillNo(Long billNo);

    @Modifying
    @Transactional
    @Query("delete from OrderDetailsWaterSport where billNo = :billNo")
    void deleteByBillNo(Long billNo);
}
