package com.kc1.dto;

import com.kc1.entities.Application.ApplicationStatus;

public class ApplicationResponseDto {
	private Integer applicationId;
    private Integer userId;
    private String userName;
    private Integer collegeId;
    private String collegeName;
    private Integer courseId;
    private String courseName;
    private ApplicationStatus applicationStatus;
	public ApplicationResponseDto(Integer applicationId, Integer userId, String userName, Integer collegeId, String collegeName, Integer courseId,
			String courseName, ApplicationStatus applicationStatus) {
		super();
		this.applicationId = applicationId;
		this.userId = userId;
		this.userName = userName;
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.courseId = courseId;
		this.courseName = courseName;
		this.applicationStatus = applicationStatus;
	}
    
    public ApplicationResponseDto() {
		// TODO Auto-generated constructor stub
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
