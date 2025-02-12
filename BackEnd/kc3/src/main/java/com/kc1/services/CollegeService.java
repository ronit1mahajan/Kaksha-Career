package com.kc1.services;

import java.util.List;

import com.kc1.dto.CollegeDto;
import com.kc1.entities.College;

public interface CollegeService {
	College addCollege(CollegeDto collegeDto);
    College updateCollege(Integer collegeId, CollegeDto collegeDto);
    void deleteCollege(Integer collegeId);
    List<CollegeDto> getAllColleges();
    College getCollegeById(Integer collegeId);
    List<College> searchCollegesByName(String name);
	List<College> getCollegesByType(College.Type type);
}
