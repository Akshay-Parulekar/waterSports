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
    @Query("select count(id) from Referee where idOwner = :idRef")
    Integer checkOwner(Long idRef);

    List<Referee> findAllByOrderByIdOwnerAscName();

    @Query("from Referee where id = idOwner order by name")
    List<Referee> findOwners();

    List<Referee> findByIdOwnerOrderByName(Long idOwner);
}
