package com.wongsito.ic.modules.registrations.entities;

import com.wongsito.ic.modules.courses.entities.Courses;
import com.wongsito.ic.modules.students.entities.Students;
import jakarta.persistence.*;

@Entity
@Table(name = "registrations")
public class Registrations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses courses;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;

    @Column
    private Integer assists;

    @Column
    private Float qualification;

    @Column
    private Boolean approved;

    public Registrations() {
    }

    public Registrations(Long id, Courses courses, Students student, Integer assists, Float qualification, Boolean approved) {
        this.id = id;
        this.courses = courses;
        this.student = student;
        this.assists = assists;
        this.qualification = qualification;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Float getQualification() {
        return qualification;
    }

    public void setQualification(Float qualification) {
        this.qualification = qualification;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}