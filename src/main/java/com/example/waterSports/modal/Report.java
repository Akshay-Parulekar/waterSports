package com.example.waterSports.modal;

public class Report
{
    Integer day;
    Integer month;
    Integer year;
    Double rate;

    public Report(Integer day, Integer month, Integer year, Double rate) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.rate = rate;
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

    @Override
    public String toString() {
        return "Report{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", rate=" + rate +
                '}';
    }
}
