package com.kc1.dto;

import java.time.LocalDate;

public class ReviewDTO {
    private Integer reviewId;
    private String userName;
    private int rating;
    private String comment;
    private LocalDate createdAt;

    public ReviewDTO(Integer reviewId, String userName, int rating, String comment, LocalDate createdAt) {
        this.reviewId = reviewId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

    
}

