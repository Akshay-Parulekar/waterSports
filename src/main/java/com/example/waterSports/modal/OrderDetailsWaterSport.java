package com.example.waterSports.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderDetailsWaterSport
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long billNo;
    Integer idActivity;
    Boolean bigRound;
    Integer persons;
    Double rate;

    public OrderDetailsWaterSport() {
    }

    public OrderDetailsWaterSport(Long billNo, Integer idActivity, Boolean bigRound, Integer persons, Double rate) {
        this.billNo = billNo;
        this.idActivity = idActivity;
        this.bigRound = bigRound;
        this.persons = persons;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillNo() {
        return billNo;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public Integer getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Integer idActivity) {
        this.idActivity = idActivity;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Boolean isBigRound() {
        return bigRound;
    }

    public void setBigRound(Boolean bigRound) {
        this.bigRound = bigRound;
    }

    @Override
    public String toString() {
        return "OrderDetailsWaterSport{" +
                "id=" + id +
                ", billNo=" + billNo +
                ", idActivity=" + idActivity +
                ", isBigRound=" + bigRound +
                ", persons=" + persons +
                ", rate=" + rate +
                '}';
    }
}
