package com.kc1.repositories;

import com.kc1.entities.Application;
import com.kc1.entities.Application.ApplicationStatus;
import com.kc1.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationDao extends JpaRepository<Application, Integer> {
	@Query("SELECT a FROM Application a WHERE a.user.id = :userId")
	List<Application> findByUserId(@Param("userId") Integer userId);

	List<Application> findByUser(User user);
	
	@Query("SELECT a FROM Application a WHERE a.user.userId = :userId AND a.applicationStatus=:applicationStatus")
	List<Application> findByUserIdAndApplicationStatus(Integer userId, ApplicationStatus applicationStatus);
	
	@Query("SELECT a FROM Application a WHERE a.college.id = :collegeId")
	List<Application> findByCollegeId(@Param("collegeId") Integer collegeId);

    @Query("SELECT a FROM Application a WHERE a.course.id = :courseId")
    List<Application> findByCourseId(@Param("courseId") Integer courseId);
    
    List<Application> findByApplicationStatus(ApplicationStatus applicationStatus);

    @Query("SELECT a FROM Application a WHERE a.user.id = :userId AND a.college.id = :collegeId")
    Optional<Application> findByUserIdAndCollegeId(@Param("userId") Integer userId, @Param("collegeId") Integer collegeId);
}
