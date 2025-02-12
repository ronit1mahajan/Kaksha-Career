package com.kc1.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kc1.dto.AddCourseDto;
import com.kc1.dto.CourseDto;
import com.kc1.entities.College;
import com.kc1.entities.Course;
import com.kc1.exceptions.ResourceNotFoundException;
import com.kc1.repositories.CollegeDao;
import com.kc1.repositories.CourseDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CollegeDao collegeDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseDto addCourse(AddCourseDto courseDto) {
        Integer collId= courseDto.getCollegeId();
    	
        Course course = new Course(
        		courseDto.getName(), courseDto.getDuration(), courseDto.getFee(), courseDto.getEligibilityCriteria());
        		

        Course saved = courseDao.save(course);
        CourseDto newDto= addCourseToCollege(collId, saved.getCourseId());
        
        return newDto; 
    }

    @Override
    public CourseDto updateCourse(Integer courseId, CourseDto courseDto) {
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }
        if (courseDto.getDuration() != null) {
            course.setDuration(courseDto.getDuration());
        }
        if (courseDto.getFee() != null) {
            course.setFee(courseDto.getFee());
        }
        if (courseDto.getEligibilityCriteria() != null) {
            course.setEligibilityCriteria(courseDto.getEligibilityCriteria());
        }

        course = courseDao.save(course);
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        courseDao.delete(course);
    }

    @Override
    public CourseDto getCourseById(Integer courseId) {
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return modelMapper.map(course, CourseDto.class); 
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseDao.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> searchCoursesByName(String courseName) {
        return courseDao.findByNameContainingIgnoreCase(courseName); 
    }

    @Override
    public List<Course> searchCoursesByDuration(String duration) {
        return courseDao.findByDurationContainingIgnoreCase(duration);
    }

    @Override
    public List<Course> searchCoursesByFeeRange(Double minFee, Double maxFee) {
        return courseDao.findByFeeBetween(minFee, maxFee);
    }

    @Override
    public List<Course> searchCoursesByEligibility(String eligibilityCriteria) {
        return courseDao.findByEligibilityCriteriaContainingIgnoreCase(eligibilityCriteria); 
    }
    
    @Override
    public List<CourseDto> getAllCoursesWithCollege() {
        return courseDao.findAllCoursesWithCollege();
    }

	@Override
	public CourseDto addCourseToCollege(Integer collegeId, Integer courseId) {
		College college=collegeDao.findById(collegeId).orElseThrow(() -> new RuntimeException("College not found"));
		Course course= courseDao.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
		course.setCollege(college);
		CourseDto cDto=modelMapper.map(course, CourseDto.class);
		return cDto;
	}

	
	
}
