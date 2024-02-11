package com.example.waterSports.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Referee
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    Long idOwner;

    public Referee() {
    }

    public Referee(String name, Long idOwner)
    {
        this.name = name;
        this.idOwner = idOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    @Override
    public String toString() {
        return "Referee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idOwner=" + idOwner +
                '}';
    }
}
