package com.example.waterSports.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderWaterSport
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long billNo;
    String customerName;
    Long idRef;
    String receiptNo;
    Boolean paid;
    @CreationTimestamp
    @Column(columnDefinition = "DATETIME")
    LocalDateTime date;

    public OrderWaterSport() {
    }

    public OrderWaterSport(Long billNo, String customerName, Long idRef, String receiptNo, Boolean paid) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.idRef = idRef;
        this.receiptNo = receiptNo;
        this.paid = paid;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getIdRef() {
        return idRef;
    }

    public void setIdRef(Long idRef) {
        this.idRef = idRef;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "OrderWaterSport{" +
                "id=" + id +
                ", billNo=" + billNo +
                ", customerName='" + customerName + '\'' +
                ", idRef=" + idRef +
                ", receiptNo='" + receiptNo + '\'' +
                ", paid=" + paid +
                ", date=" + date +
                '}';
    }
}
