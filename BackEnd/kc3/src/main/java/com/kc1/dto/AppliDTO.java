package com.kc1.dto;

import com.kc1.entities.Application.ApplicationStatus;

public class AppliDTO {
	private Integer applicationId;
	private Integer userId;
	private Integer courseId;
	private Integer collegeId;
	private ApplicationStatus applicationStatus;
	
	
	
	public Integer getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
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
	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	public AppliDTO() {
		// TODO Auto-generated constructor stub
	}
	public AppliDTO(Integer applicationId, Integer userId, Integer courseId, Integer collegeId,
			ApplicationStatus applicationStatus) {
		super();
		this.applicationId = applicationId;
		this.userId = userId;
		this.courseId = courseId;
		this.collegeId = collegeId;
		this.applicationStatus = applicationStatus;
	}
	public AppliDTO(Integer userId, Integer courseId, Integer collegeId,
			ApplicationStatus applicationStatus) {
		super();
		this.userId = userId;
		this.courseId = courseId;
		this.collegeId = collegeId;
		this.applicationStatus = applicationStatus;
	}
	@Override
	public String toString() {
		return "AppliDTO [applicationId=" + applicationId + ", userId=" + userId + ", courseId=" + courseId
				+ ", collegeId=" + collegeId + ", applicationStatus=" + applicationStatus + "]";
	}
	
	
	
}
