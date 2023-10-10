package com.example.waterSports.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class ActivityLog
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    LocalDateTime timestampAct;
    String details;

    public ActivityLog() {
    }

    public ActivityLog(String details) {
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestampAct() {
        return timestampAct;
    }

    public void setTimestampAct(LocalDateTime timestampAct) {
        this.timestampAct = timestampAct;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
