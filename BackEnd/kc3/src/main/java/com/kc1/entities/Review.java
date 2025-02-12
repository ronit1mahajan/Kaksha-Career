package com.kc1.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Review_ID")
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "College_ID", nullable = false)
    private College college;

    @Column(name = "Rating", nullable = false)
    private int rating;

    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    public Review() {
    }

    public Review(User user, College college, int rating, String comment, LocalDate date) {
        this.user = user;
        this.college = college;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", user=" + user.getName() + 
                ", college=" + college.getName() + 
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}

