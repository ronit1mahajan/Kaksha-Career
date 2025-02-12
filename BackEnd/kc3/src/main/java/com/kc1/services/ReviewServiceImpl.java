package com.kc1.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kc1.dto.AddReviewDto;
import com.kc1.dto.ReviewDTO;
import com.kc1.entities.College;
import com.kc1.entities.Review;
import com.kc1.entities.User;
import com.kc1.repositories.ReviewDao;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
		@Autowired
		private ReviewDao reviewDao;
		
	    @Autowired
	    private EmailService emailService;
	
		@Override
		public void addReview(AddReviewDto reviewDto) {
	        Review review = new Review();
	        review.setUser(new User(reviewDto.getUserId()));
	        review.setCollege(new College(reviewDto.getCollegeId()));
	        review.setRating(reviewDto.getRating());
	        review.setComment(reviewDto.getComment());
	        review.setDate(LocalDate.now());
	        Review saved = reviewDao.save(review);
	        emailService.sendEmail(saved.getUser().getEmail(), "Review Submitted Successfully",
	                "Dear Student,\n\nYour review for " + saved.getCollege().getName() + " has been submitted.\n\nThanks!");

	    }
		
	    @Override
	    public Review saveReview(Review review) {
	        return reviewDao.save(review);
	    }

	    @Override
	    public Review updateReview(Integer reviewId, Review review) {
	        Optional<Review> existingReview = reviewDao.findByReviewId(reviewId);
	        if (existingReview.isPresent()) {
	            review.setReviewId(reviewId);
	            return reviewDao.save(review); 
	        }
	        return null; 
	    }

	    @Override
	    public void deleteReview(Integer reviewId) {
	        reviewDao.deleteById(reviewId); 
	    }

	    @Override
	    public Optional<Review> getReviewById(Integer reviewId) {
	        return reviewDao.findByReviewId(reviewId);
	    }

	    @Override
	    public List<ReviewDTO> getReviewsByCollegeId(Integer collegeId) {
	    	List<Review> reviews= reviewDao.findByCollegeCollegeId(collegeId); 
	    	return reviews.stream()
	                .map(review -> new ReviewDTO(
	                    review.getReviewId(),
	                    review.getUser().getName(),
	                    review.getRating(),
	                    review.getComment(),
	                    review.getDate()))
	                .collect(Collectors.toList());
	    }

	    @Override
	    public List<Review> getReviewsByUserId(int userId) {
	        return reviewDao.findByUser_UserId(userId); 
	    }

	    @Override
	    public List<Review> getReviewsByCollegeName(String collegeName) {
	        return reviewDao.findByCollege_Name(collegeName); 
	    }

		@Override
		public double getAverageRating(int collegeId) {
		    List<Review> reviews = reviewDao.findByCollegeCollegeId(collegeId);
		    return reviews.stream()
		                  .mapToInt(Review::getRating)
		                  .average()
		                  .orElse(0.0);
		}

		@Override
		public List<Review> getReviewsByRating(int rating) {
		    return reviewDao.findByRating(rating);
		}

		@Override
		public List<Review> getRecentReviewsForCollege(int collegeId) {
		    return reviewDao.findTop5ByCollegeIdOrderByDateDesc(collegeId);
		}



}
