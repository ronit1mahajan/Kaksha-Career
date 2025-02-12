package com.kc1.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Course_ID")
    private Integer courseId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Duration", length = 50)
    private String duration;

    @Column(name = "Fee", precision = 10, scale = 2)
    private BigDecimal fee;

    @Column(name = "Eligibility_Criteria", columnDefinition = "TEXT")
    private String eligibilityCriteria;

    @ManyToOne
    @JoinColumn(name = "College_ID", nullable = true)
    private College college;
    
    public Course() {
    }

    public Course(String name, String duration, BigDecimal fee, String eligibilityCriteria) {
        this.name = name;
        this.duration = duration;
        this.fee = fee;
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                ", eligibilityCriteria='" + eligibilityCriteria + '\'' +
                ", college=" + college.getName() + 
                '}';
    }
}

