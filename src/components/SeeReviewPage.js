import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './allcss/SeeReviewPage.css';

const SeeReviewsPage = () => {
  const { collegeId } = useParams(); // Get collegeId from URL params
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        // Get token from localStorage (if available)
        const token = localStorage.getItem('authToken');
        
        const response = await axios.get(`http://localhost:7070/reviews/college/${collegeId}`, {
          headers: {
            Authorization: token ? `Bearer ${token}` : '', // Include the token if available
          },
        });
        
        setReviews(response.data);
        setLoading(false);
      } catch (error) {
        setError('Error fetching reviews');
        setLoading(false);
      }
    };

    fetchReviews();
  }, [collegeId]);

  return (
    <div className="see-reviews-page">
      <h2>Reviews for College</h2>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>{error}</p>
      ) : reviews.length === 0 ? (
        <p>No reviews available for this college.</p>
      ) : (
        <div className="reviews-list">
          {reviews.map((review) => (
            <div className="review-card" key={review.reviewId}>
              <h3>{review.userName}</h3>
              <p>Rating: {review.rating}/5</p>
              <p>Comment: {review.comment}</p>
              <p>Date: {new Date(review.createdAt).toLocaleDateString()}</p>
            </div>
          ))}
        </div>
      )}
      <button className="back-button" onClick={() => navigate(-1)}>Back</button>
    </div>
  );
};

export default SeeReviewsPage;
