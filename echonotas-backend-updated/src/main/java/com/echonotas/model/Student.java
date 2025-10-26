package com.echonotas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tb_students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String course;
    private Integer yearCourse;
    private LocalDate enrollmentDate;

    public Student() {}

    public Student(String name, String email, String course, Integer yearCourse, LocalDate enrollmentDate) {
        this.name = name;
        this.email = email;
        this.course = course;
        this.yearCourse = yearCourse;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public Integer getYearCourse() { return yearCourse; }
    public void setYearCourse(Integer yearCourse) { this.yearCourse = yearCourse; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
