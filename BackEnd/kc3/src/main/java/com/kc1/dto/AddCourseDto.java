package com.kc1.dto;

import java.math.BigDecimal;

public class AddCourseDto {
	private String name;
    private String duration;
    private BigDecimal fee;
    private String eligibilityCriteria;
    private Integer collegeId;
    
    public AddCourseDto() {
		// TODO Auto-generated constructor stub
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

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	@Override
	public String toString() {
		return "AddCourseDto [name=" + name + ", duration=" + duration + ", fee=" + fee + ", eligibilityCriteria="
				+ eligibilityCriteria + ", collegeId=" + collegeId + "]";
	}

	public AddCourseDto(String name, String duration, BigDecimal fee, String eligibilityCriteria, Integer collegeId) {
		super();
		this.name = name;
		this.duration = duration;
		this.fee = fee;
		this.eligibilityCriteria = eligibilityCriteria;
		this.collegeId = collegeId;
	}
    
}
