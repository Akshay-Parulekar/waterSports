package com.example.waterSports.repo;

import com.example.waterSports.modal.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepo extends JpaRepository<Referee, Long>
{

}
