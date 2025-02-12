import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import './allcss/AddReviewForm.css';

const AddReviewPage = () => {
  const { collegeId } = useParams(); // Get collegeId from URL
  const [collegeName, setCollegeName] = useState('');
  const [rating, setRating] = useState(1);
  const [comment, setComment] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Ensure only students can access this page
    const userRole = localStorage.getItem('userRole');
    if (userRole !== 'STUDENT') {
      alert("Only students can add reviews!");
      navigate('/');
      return;
    }

    // Fetch college name (optional, but improves UX)
    const fetchCollegeDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:7070/colleges/${collegeId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('authToken')}`,
          },
        });
        setCollegeName(response.data.collegeName);
      } catch (error) {
        setError('Failed to fetch college details.');
      }
    };

    fetchCollegeDetails();
  }, [collegeId, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userId = JSON.parse(localStorage.getItem('user')).userId; // Get logged-in user's ID

    if (!comment.trim()) {
      setError("Review text can't be empty.");
      return;
    }

    const reviewData = {
      userId,
      collegeId,
      rating,
      comment,
    };

    try {
      await axios.post('http://localhost:7070/reviews/add', reviewData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('authToken')}`,
        },
      });
      alert("Review added successfully!");
      navigate(`/reviews/${collegeId}`); // Redirect to see reviews
    } catch (error) {
      setError('Error submitting review. Please try again.');
    }
  };

  return (
    <div className="add-review-container">
      <h2>Add Review for {collegeName || "College"}</h2>

      {error && <p className="error-message">{error}</p>}

      <form onSubmit={handleSubmit}>
        <label>Rating (1-5):</label>
        <select value={rating} onChange={(e) => setRating(Number(e.target.value))}>
          {[1, 2, 3, 4, 5].map((num) => (
            <option key={num} value={num}>{num}</option>
          ))}
        </select>

        <label>Review:</label>
        <textarea
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="Write your review here..."
          rows="4"
        ></textarea>

        <button type="submit">Submit Review</button>
      </form>

      <button className="back-button" onClick={() => navigate(`/review`)}>
        Back to Reviews
      </button>
    </div>
  );
};

export default AddReviewPage;
