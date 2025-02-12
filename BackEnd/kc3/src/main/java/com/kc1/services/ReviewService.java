package com.kc1.services;

import com.kc1.dto.AddReviewDto;
import com.kc1.dto.ReviewDTO;
import com.kc1.entities.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review saveReview(Review review);

    void addReview(AddReviewDto reviewDto);

    Review updateReview(Integer reviewId, Review review);

    void deleteReview(Integer reviewId);

    Optional<Review> getReviewById(Integer reviewId);

    List<ReviewDTO> getReviewsByCollegeId(Integer collegeId);

    List<Review> getReviewsByUserId(int userId);

    List<Review> getReviewsByCollegeName(String collegeName);

	double getAverageRating(int collegeId);

	List<Review> getReviewsByRating(int rating);

	List<Review> getRecentReviewsForCollege(int collegeId);
}
