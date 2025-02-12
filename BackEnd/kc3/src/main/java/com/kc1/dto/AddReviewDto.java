package com.kc1.dto;

public class AddReviewDto {
	private Integer userId;
    private Integer collegeId;
    private int rating;
    private String comment;
    
    public AddReviewDto() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
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

	public AddReviewDto(Integer userId, Integer collegeId, int rating, String comment) {
		super();
		this.userId = userId;
		this.collegeId = collegeId;
		this.rating = rating;
		this.comment = comment;
	}
    

}
