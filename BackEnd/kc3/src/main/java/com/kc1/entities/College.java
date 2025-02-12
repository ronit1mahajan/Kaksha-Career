package com.kc1.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "College")
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "College_ID")
    private Integer collegeId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Location", length = 200)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false)
    private Type type;

    @Column(name = "Affiliated_University", length = 200)
    private String affiliatedUniversity;

    @Column(name = "Established_Year")
    private Integer establishedYear;

    @Column(name = "Contact_Info", length = 255)
    private String contactInfo;
    
    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    public enum Type {
        Engineering, Management, Commerce, Arts
    }

    public College() {
    }

    public College(String name, String location, Type type, String affiliatedUniversity, Integer establishedYear, String contactInfo) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.affiliatedUniversity = affiliatedUniversity;
        this.establishedYear = establishedYear;
        this.contactInfo = contactInfo;
    }

    

	public College(Integer collegeId) {
		super();
		this.collegeId = collegeId;
	}

	// Getters and Setters
    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

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
    
    

    @Override
    public String toString() {
        return "College{" +
                "collegeId=" + collegeId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", type=" + type +
                ", affiliatedUniversity='" + affiliatedUniversity + '\'' +
                ", establishedYear=" + establishedYear +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }

}

