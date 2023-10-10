package com.example.waterSports.repo;

import com.example.waterSports.modal.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepo extends JpaRepository<ActivityLog, Long>
{
    List<ActivityLog> findByTimestampActBetweenOrderByTimestampActDesc(LocalDateTime startDate, LocalDateTime endDate);
}
