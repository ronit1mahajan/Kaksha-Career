package com.kc1.repositories;

import com.kc1.dto.CourseDto;
import com.kc1.entities.College;
import com.kc1.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

    Optional<Course> findById(Integer courseId);

    List<Course> findAll();

    List<Course> findByCollege_Name(String collegeName);

    List<Course> findByName(String name);

    List<Course> findByDurationContainingIgnoreCase(String duration);

    List<Course> findByFeeBetween(Double minFee, Double maxFee);

    List<Course> findByEligibilityCriteriaContainingIgnoreCase(String eligibilityCriteria);
    
    List<Course> findByNameContainingIgnoreCase(String name);

    List<Course> findByCollege(College college);

    Optional<Course> findByNameAndCollege(String name, College college);

    @Query("SELECT new com.kc1.dto.CourseDto(c.courseId, c.name, c.duration, c.fee, c.eligibilityCriteria, c.college.name,c.college.location,c.college.collegeId) FROM Course c")
    List<CourseDto> findAllCoursesWithCollege();
    
	List<Course> findByCollegeCollegeId(Integer collegeId);
}
