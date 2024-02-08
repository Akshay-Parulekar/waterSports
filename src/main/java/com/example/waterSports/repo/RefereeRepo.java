package com.example.waterSports.repo;

import com.example.waterSports.modal.Referee;
import com.example.waterSports.modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RefereeRepo extends JpaRepository<Referee, Long>
{

}
