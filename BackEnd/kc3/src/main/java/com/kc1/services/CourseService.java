package com.kc1.services;

import java.util.List;

import com.kc1.dto.AddCourseDto;
import com.kc1.dto.CourseDto;
import com.kc1.entities.Course;
import com.kc1.exceptions.ResourceNotFoundException;

public interface CourseService {

    List<CourseDto> getAllCourses();

    CourseDto getCourseById(Integer courseId) throws ResourceNotFoundException;

    List<Course> searchCoursesByName(String courseName);
    
    List<Course> searchCoursesByDuration(String duration);
    
    List<Course> searchCoursesByFeeRange(Double minFee, Double maxFee);

    List<Course> searchCoursesByEligibility(String eligibilityCriteria);

    CourseDto updateCourse(Integer courseId, CourseDto courseDto) throws ResourceNotFoundException;

    void deleteCourse(Integer courseId) throws ResourceNotFoundException;

    CourseDto addCourseToCollege(Integer collegeId,Integer courseId);

	CourseDto addCourse(AddCourseDto courseDto);
	
	List<CourseDto> getAllCoursesWithCollege();
}
