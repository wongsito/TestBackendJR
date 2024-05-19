package com.wongsito.ic.modules.courses.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeApproval typeApproval;

    public enum TypeApproval {
        assists, qualification, mixed
    }

    public Courses() {
    }

    public Courses(Long id, String name, String description, TypeApproval typeApproval) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.typeApproval = typeApproval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeApproval getTypeApproval() {
        return typeApproval;
    }

    public void setTypeApproval(TypeApproval typeApproval) {
        this.typeApproval = typeApproval;
    }
}
