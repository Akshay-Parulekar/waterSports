package com.example.waterSports.repo;

import com.example.waterSports.modal.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepo extends JpaRepository<Config, String>
{
    String findOneByProp(String prop);
}
