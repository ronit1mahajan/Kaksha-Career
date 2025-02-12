package com.kc1.dto;

import com.kc1.entities.Application.ApplicationStatus;

public class ApplicationDTO {

    private Integer collegeId;
    private String collegeName;
    private ApplicationStatus applicationStatus;

    public ApplicationDTO() {
		// TODO Auto-generated constructor stub
	}
    
    public ApplicationDTO(Integer collegeId, String collegeName, ApplicationStatus applicationStatus) {
        this.collegeId = collegeId;
        this.collegeName = collegeName;
        this.applicationStatus = applicationStatus;
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

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
