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
    private Long workTime;
    private boolean isEditable;

//    Table dependencies
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    Constructors
    public Record() {
        recordType = RecordType.work;
        isEditable = true;
    }

    public Record(Integer id, LocalDateTime start, LocalDateTime end,
                  String project, String body, RecordPlace place,
                  RecordType recordType, User user) {
        this.start = start;
        this.end = end;
        this.project = project;
        this.body = body;
        this.place = place;
        this.recordType = recordType;
        this.user = user;
        workTimeDuration(start, end);
        this.isEditable = true;
    }

//    Methods
    private void workTimeDuration(LocalDateTime start, LocalDateTime end) {
        this.workTime = Duration.between(start, end).toMinutes();
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

    public void setWorkTime() {
        workTimeDuration(this.start, this.end);
    }

    public Long getWorkTime() {
        return workTime;
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean editable) {
        isEditable = editable;
    }
}
