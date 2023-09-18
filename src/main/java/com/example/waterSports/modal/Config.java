package com.example.waterSports.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Config
{
    @Id
    String prop;
    String val;

    public Config() {
    }

    public Config(String prop, String val) {
        this.prop = prop;
        this.val = val;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Config{" +
                "prop='" + prop + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
