package com.wongsito.ic.modules.registrations.use_cases.dtos;

public class RegistrationDTOImpl implements RegistrationsDTO{

    private long courseId;
    private long studentId;
    private Integer assists;
    private Float qualification;

    @Override
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    @Override
    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    @Override
    public Float getQualification() {
        return qualification;
    }

    public void setQualification(Float qualification) {
        this.qualification = qualification;
    }
}
