package com.project_1.ecp_v1.model;

import com.project_1.ecp_v1.enums.UserPositions;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dateOfBirth;
    private UserPositions position;
    private Boolean isHired;
    private LocalDate employmentDate;
    private LocalDate releaseDate;

//    Table dependencies
    @OneToMany(mappedBy = "user")
    List<Record> records;

//    Constructors
    public User() {
        this.releaseDate = LocalDate.of(2099,12,31);
        this.isHired = true;
    }

//    Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserPositions getPosition() {
        return position;
    }

    public void setPosition(UserPositions position) {
        this.position = position;
    }

    public Boolean getIsHired() {
        return isHired;
    }

    public void setIsHired(Boolean isHired) {
        this.isHired = isHired;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate != null) {
            this.releaseDate = releaseDate;
        }
    }
}
