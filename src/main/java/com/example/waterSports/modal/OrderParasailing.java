package com.example.waterSports.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class OrderParasailing
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long billNo;
    String customerName;
    String contact;
    Long idRef;
    String serialNo;
    Double rate;
    Integer nPerson;
    @CreationTimestamp
    LocalDate date;

    public OrderParasailing() {
    }

    public OrderParasailing(Long billNo, String customerName, String contact, Double rate, Integer nPerson, Long idRef, String serialNo) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.contact = contact;
        this.rate = rate;
        this.nPerson = nPerson;
        this.idRef = idRef;
        this.serialNo = serialNo;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getnPerson() {
        return nPerson;
    }

    public void setnPerson(Integer nPerson) {
        this.nPerson = nPerson;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getIdRef() {
        return idRef;
    }

    public void setIdRef(Long idRef) {
        this.idRef = idRef;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return "OrderParasailing{" +
                "id=" + id +
                ", billNo=" + billNo +
                ", customerName='" + customerName + '\'' +
                ", contact='" + contact + '\'' +
                ", idRef=" + idRef +
                ", serialNo='" + serialNo + '\'' +
                ", rate=" + rate +
                ", nPerson=" + nPerson +
                ", date=" + date +
                '}';
    }
}
