package com.kc1.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kc1.dto.CollegeDto;
import com.kc1.entities.College;
//import com.kc1.entities.EntranceExam;
import com.kc1.repositories.CollegeDao;
//import com.kc1.repositories.EntranceExamDao;

@Service
@Transactional
public class CollegeServiceImpl implements CollegeService {

	 	@Autowired
	    private CollegeDao collegeDao;

	 	@Override
	    public College addCollege(CollegeDto collegeDto) {
	        College college = new College();
	        college.setName(collegeDto.getName());
	        college.setLocation(collegeDto.getLocation());
	        college.setType(collegeDto.getType());
	        college.setAffiliatedUniversity(collegeDto.getAffiliatedUniversity());
	        college.setEstablishedYear(collegeDto.getEstablishedYear());
	        college.setContactInfo(collegeDto.getContactInfo());

	        return collegeDao.save(college);
	    }

	    @Override
	    public College updateCollege(Integer collegeId, CollegeDto collegeDto) {
	        College college = collegeDao.findById(collegeId)
	                .orElseThrow(() -> new RuntimeException("College not found"));

	        if (collegeDto.getName() != null) college.setName(collegeDto.getName());
	        if (collegeDto.getLocation() != null) college.setLocation(collegeDto.getLocation());
	        if (collegeDto.getType() != null) college.setType(collegeDto.getType());
	        if (collegeDto.getAffiliatedUniversity() != null) college.setAffiliatedUniversity(collegeDto.getAffiliatedUniversity());
	        if (collegeDto.getEstablishedYear() != null) college.setEstablishedYear(collegeDto.getEstablishedYear());
	        if (collegeDto.getContactInfo() != null) college.setContactInfo(collegeDto.getContactInfo());

	        return collegeDao.save(college);
	    }

	    @Override
	    public void deleteCollege(Integer collegeId) {
	        College college = collegeDao.findById(collegeId)
	                .orElseThrow(() -> new RuntimeException("College not found"));
	        collegeDao.delete(college);
	    }

	    @Override
	    public List<CollegeDto> getAllColleges() {
	         List<College> all = collegeDao.findAll();
	         List<CollegeDto> collDTOs = new ArrayList<>();
	         for (College coll : all) {
	        	 collDTOs.add(new CollegeDto(coll));
	         }
			return collDTOs;
	    }

	    @Override
	    public College getCollegeById(Integer collegeId) {
	        return collegeDao.findById(collegeId)
	                .orElseThrow(() -> new RuntimeException("College not found"));
	    }
	    
	    @Override
		public List<College> getCollegesByType(College.Type type) {
	        return collegeDao.findByType(type);
	    }
	    
	    @Override
	    public List<College> searchCollegesByName(String name) {
	        return collegeDao.searchCollegesByName(name); 
	    }
	    
	    
}

