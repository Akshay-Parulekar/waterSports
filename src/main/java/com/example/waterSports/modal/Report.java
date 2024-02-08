package com.example.waterSports.modal;

public class Report
{
    Integer day;
    Integer month;
    Integer year;
    Double rate;
    Long idRef;

    public Report(Integer day, Integer month, Integer year, Double rate, Long idRef) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.rate = rate;
        this.idRef = idRef;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getIdRef() {
        return idRef;
    }

    public void setIdRef(Long idRef) {
        this.idRef = idRef;
    }

    @Override
    public String toString() {
        return "Report{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", rate=" + rate +
                ", idRef=" + idRef +
                '}';
    }
}
