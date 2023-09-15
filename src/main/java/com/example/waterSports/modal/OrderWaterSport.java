package com.example.waterSports.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class OrderWaterSport
{
    @Id
    @SequenceGenerator(name = "seqWaterSport")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqWaterSport")
    Long id;
    Long billNo;
    String customerName;
    String contact;
    Double rate;
    Integer nPerson;
    Boolean jsr;
    Boolean br;
    Boolean sebr;
    Boolean slbr;
    Boolean para;
    @CreationTimestamp
    LocalDate date;

    public OrderWaterSport(Long billNo, String customerName, String contact, Double rate, Integer nPerson, Boolean jsr, Boolean br, Boolean sebr, Boolean slbr, Boolean para) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.contact = contact;
        this.rate = rate;
        this.nPerson = nPerson;
        this.jsr = jsr;
        this.br = br;
        this.sebr = sebr;
        this.slbr = slbr;
        this.para = para;
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

    public Boolean getJsr() {
        return jsr;
    }

    public void setJsr(Boolean jsr) {
        this.jsr = jsr;
    }

    public Boolean getBr() {
        return br;
    }

    public void setBr(Boolean br) {
        this.br = br;
    }

    public Boolean getSebr() {
        return sebr;
    }

    public void setSebr(Boolean sebr) {
        this.sebr = sebr;
    }

    public Boolean getSlbr() {
        return slbr;
    }

    public void setSlbr(Boolean slbr) {
        this.slbr = slbr;
    }

    public Boolean getPara() {
        return para;
    }

    public void setPara(Boolean para) {
        this.para = para;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderWaterSport{" +
                "id=" + id +
                ", billNo=" + billNo +
                ", customerName='" + customerName + '\'' +
                ", contact='" + contact + '\'' +
                ", rate=" + rate +
                ", nPerson=" + nPerson +
                ", jsr=" + jsr +
                ", br=" + br +
                ", sebr=" + sebr +
                ", slbr=" + slbr +
                ", para=" + para +
                ", date=" + date +
                '}';
    }
}
