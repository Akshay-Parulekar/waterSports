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
    Double rate;
    @CreationTimestamp
    LocalDate date;

    public OrderParasailing() {
    }

    public OrderParasailing(Long billNo, String customerName, String contact, Double rate) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.contact = contact;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderParasailing{" +
                "id=" + id +
                ", billNo=" + billNo +
                ", customerName='" + customerName + '\'' +
                ", contact='" + contact + '\'' +
                ", rate=" + rate +
                ", date=" + date +
                '}';
    }
}
