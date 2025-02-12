package com.kc1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kc1.dto.AddReviewDto;
import com.kc1.dto.ReviewDTO;
import com.kc1.entities.Review;
import com.kc1.entities.User;
import com.kc1.services.ReviewService;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody AddReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
        return ResponseEntity.ok("Review added successfully!");
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable int reviewId, @RequestBody Review review, Authentication authentication) {
        Optional<Review> existingReview = reviewService.getReviewById(reviewId);
        if (existingReview.isPresent()) {
            User loggedInUser = (User) authentication.getPrincipal();
            if (existingReview.get().getUser().getUserId() == loggedInUser.getUserId()) {
                Review updatedReview = reviewService.updateReview(reviewId, review);
                return ResponseEntity.ok(updatedReview);
            } else {
                return ResponseEntity.status(403).body(null);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId, Authentication authentication) {
        Optional<Review> existingReview = reviewService.getReviewById(reviewId);
        if (existingReview.isPresent()) {
            User loggedInUser = (User) authentication.getPrincipal();
            if (existingReview.get().getUser().getUserId() == loggedInUser.getUserId()) {
                reviewService.deleteReview(reviewId);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(403).body(null); 
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("get/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable int reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/college/{collegeId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByCollegeId(@PathVariable int collegeId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByCollegeId(collegeId);
        return reviews.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable int userId) {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/college/name/{collegeName}")
    public ResponseEntity<List<Review>> getReviewsByCollegeName(@PathVariable String collegeName) {
        List<Review> reviews = reviewService.getReviewsByCollegeName(collegeName);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/college/{collegeId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable int collegeId) {
        double averageRating = reviewService.getAverageRating(collegeId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> getReviewsByRating(@PathVariable int rating) {
        List<Review> reviews = reviewService.getReviewsByRating(rating);
        return ResponseEntity.ok(reviews);
    }

}
