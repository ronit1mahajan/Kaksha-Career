package com.kc1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Application_ID")
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "College_ID", nullable = false)
    private College college;

    @ManyToOne
    @JoinColumn(name = "Course_ID", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "Application_Status", nullable = false)
    private ApplicationStatus applicationStatus = ApplicationStatus.PENDING;

    public Application() {
    }

    public Application(User user, College college, Course course, ApplicationStatus applicationStatus) {
        this.user = user;
        this.college = college;
        this.course = course;
        this.applicationStatus = applicationStatus;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", user=" + user.getName() + 
                ", college=" + college.getName() + 
                ", course=" + course.getName() + 
                ", applicationStatus=" + applicationStatus +
                '}';
    }

    public enum ApplicationStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}

