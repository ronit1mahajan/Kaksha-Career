package com.kc1.dto;

import com.kc1.entities.College;
import com.kc1.entities.College.Type;

public class CollegeDto {
	private Integer collegeId;
    private String name;
    private String location;
    private Type type;
    private String affiliatedUniversity;
    private Integer establishedYear;
    private String contactInfo;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAffiliatedUniversity() {
        return affiliatedUniversity;
    }

    public void setAffiliatedUniversity(String affiliatedUniversity) {
        this.affiliatedUniversity = affiliatedUniversity;
    }

    public Integer getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(Integer establishedYear) {
        this.establishedYear = establishedYear;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	
	public CollegeDto() {
		// TODO Auto-generated constructor stub
	}

	public CollegeDto(College col) {
		super();
		this.collegeId = col.getCollegeId();
		this.name = col.getName();
		this.location = col.getLocation();
		this.type = col.getType();
		this.affiliatedUniversity = col.getAffiliatedUniversity();
		this.establishedYear = col.getEstablishedYear();
		this.contactInfo = col.getContactInfo();
	}

	@Override
	public String toString() {
		return "CollegeDto [collegeId=" + collegeId + ", name=" + name + ", location=" + location + ", type=" + type
				+ ", affiliatedUniversity=" + affiliatedUniversity + ", establishedYear=" + establishedYear
				+ ", contactInfo=" + contactInfo + "]";
	}
	
	
	

}
