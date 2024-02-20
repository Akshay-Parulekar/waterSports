package com.example.waterSports.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    String serialNo;
    Boolean paid;
    @CreationTimestamp
    LocalDate date;

    public OrderWaterSport() {
    }

    public OrderWaterSport(Long billNo, String customerName, Long idRef, String receiptNo, Boolean paid, String serialNo) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.idRef = idRef;
        this.receiptNo = receiptNo;
        this.paid = paid;
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
                ", serialNo='" + serialNo + '\'' +
                ", paid=" + paid +
                ", date=" + date +
                '}';
    }
}
