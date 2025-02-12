package com.kc1.dto;

public class AddAppliDto {
	private Integer userId;
	private Integer courseId;
	private Integer collegeId;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	
	public AddAppliDto() {
		// TODO Auto-generated constructor stub
	}
	public AddAppliDto(Integer userId, Integer courseId, Integer collegeId) {
		super();
		this.userId = userId;
		this.courseId = courseId;
		this.collegeId = collegeId;
	}
	@Override
	public String toString() {
		return "AddAppliDto [userId=" + userId + ", courseId=" + courseId + ", collegeId=" + collegeId + "]";
	}
	
	

}
