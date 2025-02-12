package com.kc1.repositories;

import com.kc1.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewDao extends JpaRepository<Review, Integer> {

	List<Review> findByCollegeCollegeId(Integer collegeId);

    Optional<Review> findByReviewId(Integer reviewId);

    @Query("SELECT r FROM Review r WHERE r.user.userId = :userId")
    List<Review> findByUser_UserId(Integer userId);

    @Query("SELECT r FROM Review r WHERE r.college.name = :collegeName")
    List<Review> findByCollege_Name(String collegeName);

    @Query("SELECT r FROM Review r WHERE r.college.id = :collegeId")
    List<Review> findReviewsByCollegeId(Integer collegeId);

    @Query("SELECT r FROM Review r WHERE r.rating = :rating")
    List<Review> findByRating(int rating);

    @Query("SELECT r FROM Review r WHERE r.college.id = :collegeId ORDER BY r.date DESC")
    List<Review> findTop5ByCollegeIdOrderByDateDesc(Integer collegeId);
}
