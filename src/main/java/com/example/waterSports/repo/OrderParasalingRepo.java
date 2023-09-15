package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderParasailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderParasalingRepo extends JpaRepository<OrderParasailing, Long>
{

}
