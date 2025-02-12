package com.kc1.dto;

import java.math.BigDecimal;

public class CourseDto {
    private Integer courseId;
    private String name;
    private String duration;
    private BigDecimal fee;
    private String eligibilityCriteria;
    private String collegeName;
    private String collegeLocation;
    private Integer collegeId;

    // Constructor
    
    public CourseDto() {
		// TODO Auto-generated constructor stub
	}


	public CourseDto(Integer courseId, String name, String duration, BigDecimal fee, String eligibilityCriteria,
			String collegeName, String collegeLocation, Integer collegeId) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.duration = duration;
		this.fee = fee;
		this.eligibilityCriteria = eligibilityCriteria;
		this.collegeName = collegeName;
		this.collegeLocation = collegeLocation;
		this.collegeId = collegeId;
	}


	public Integer getCollegeId() {
		return collegeId;
	}


	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}


	public String getCollegeLocation() {
		return collegeLocation;
	}

	public void setCollegeLocation(String collegeLocation) {
		this.collegeLocation = collegeLocation;
	}

	// Getters & Setters
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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
