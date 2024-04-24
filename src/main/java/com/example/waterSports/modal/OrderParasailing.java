package com.example.waterSports.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class OrderParasailing
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long billNo;
    String customerName;
    Long idRef;
    String receiptNo;
    Boolean bigRound;
    Boolean paid;
    Double rate;
    Integer nPerson;
    @CreationTimestamp
    @Column(columnDefinition = "DATETIME")        
    LocalDateTime date;

    public OrderParasailing() {
    }

    public OrderParasailing(Long billNo, String customerName, Double rate, Integer nPerson, Long idRef, String receiptNo, Boolean bigRound, Boolean paid) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.rate = rate;
        this.nPerson = nPerson;
        this.idRef = idRef;
        this.receiptNo = receiptNo;
        this.bigRound = bigRound;
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

    public Boolean isBigRound() {
        return bigRound;
    }

    public void setBigRound(Boolean bigRound) {
        this.bigRound = bigRound;
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
        return "OrderParasailing{" +
                "id=" + id +
                ", billNo=" + billNo +
                ", customerName='" + customerName + '\'' +
                ", idRef=" + idRef +
                ", receiptNo='" + receiptNo + '\'' +
                ", bigRound=" + bigRound +
                ", paid=" + paid +
                ", rate=" + rate +
                ", nPerson=" + nPerson +
                ", date=" + date +
                '}';
    }
}
