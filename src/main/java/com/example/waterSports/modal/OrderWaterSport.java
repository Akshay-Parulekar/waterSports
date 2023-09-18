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
    String contact;
    Double rate;
    Integer nPerson;
    Boolean jsr;
    Boolean br;
    Boolean sebr;
    Boolean slbr;
    @CreationTimestamp
    LocalDate date;

    public OrderWaterSport() {
    }

    public OrderWaterSport(Long billNo, String customerName, String contact, Double rate, Integer nPerson, Boolean jsr, Boolean br, Boolean sebr, Boolean slbr) {
        this.billNo = billNo;
        this.customerName = customerName;
        this.contact = contact;
        this.rate = rate;
        this.nPerson = nPerson;
        this.jsr = checkNull(jsr);
        this.br = checkNull(br);
        this.sebr = checkNull(sebr);
        this.slbr = checkNull(slbr);
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
        this.jsr = checkNull(jsr);
    }

    public Boolean getBr() {
        return br;
    }

    public void setBr(Boolean br) {
        this.br = checkNull(br);
    }

    public Boolean getSebr() {
        return sebr;
    }

    public void setSebr(Boolean sebr) {
        this.sebr = checkNull(sebr);
    }

    public Boolean getSlbr() {
        return slbr;
    }

    public void setSlbr(Boolean slbr) {
        this.slbr = checkNull(slbr);
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
                ", date=" + date +
                '}';
    }

    public String getActivities()
    {
        List<String> list = new ArrayList<>();

        if(jsr)
        {
            list.add("Jet Sky Ride");
        }
        if(br)
        {
            list.add("Banana Ride");
        }
        if(sebr)
        {
            list.add("Seating Bumper Ride");
        }
        if(slbr)
        {
            list.add("Sleeping Bumper Ride");
        }

        return String.join(", ", list);
    }

    public boolean checkNull(Boolean b)
    {
        if(b == null)
        {
            return false;
        }
        else
        {
            return b;
        }
    }
}
