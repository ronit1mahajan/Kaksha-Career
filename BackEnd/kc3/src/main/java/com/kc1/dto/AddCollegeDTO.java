package com.kc1.dto;

import com.kc1.entities.College.Type;

public class AddCollegeDTO {
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
	
	public AddCollegeDTO() {
		// TODO Auto-generated constructor stub
	}

	public AddCollegeDTO(String name, String location, Type type, String affiliatedUniversity,
			Integer establishedYear, String contactInfo) {
		super();
		this.name = name;
		this.location = location;
		this.type = type;
		this.affiliatedUniversity = affiliatedUniversity;
		this.establishedYear = establishedYear;
		this.contactInfo = contactInfo;
	}

	@Override
	public String toString() {
		return "CollegeDto [name=" + name + ", location=" + location + ", type=" + type
				+ ", affiliatedUniversity=" + affiliatedUniversity + ", establishedYear=" + establishedYear
				+ ", contactInfo=" + contactInfo + "]";
	}
	
	
	

}
