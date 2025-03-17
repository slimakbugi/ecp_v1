package com.project_1.ecp_v1.model;

import com.project_1.ecp_v1.enums.RecordPlace;
import com.project_1.ecp_v1.enums.RecordType;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String project;
    private String body;
    private RecordPlace place;
    private RecordType recordType;
    private Duration workTime;

//    Table dependencies
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    Constructors
    public Record() {
        recordType = RecordType.work;
        workTime = Duration.between(start, end);
    }



//    Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public RecordPlace getPlace() {
        return place;
    }

    public void setPlace(RecordPlace place) {
        this.place = place;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
