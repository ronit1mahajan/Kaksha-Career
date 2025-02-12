import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './allcss/AddReviewPage.css';

const AddReviewPage = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // UseEffect to fetch accepted applications for a logged-in student
  useEffect(() => {
    const fetchAcceptedApplications = async () => {
      const userId = JSON.parse(localStorage.getItem('user')).userId; // Get the userId from localStorage

      if (!userId) {
        alert("User is not logged in!");
        navigate('/');  // Redirect to home or login page
        return;
      }

      try {
        // Make the API call to fetch accepted applications using userId as a path parameter
        const response = await axios.get(`http://localhost:7070/applications/accepted/${userId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('authToken')}`,
          },
        });
        setApplications(response.data);
        setLoading(false);
      } catch (error) {
        setError('Error fetching applications');
        setLoading(false);
      }
    };

    // Ensure only users with role 'STUDENT' can access the page
    const userRole = localStorage.getItem('userRole');
    if (userRole !== 'STUDENT') {
      alert("Only a logged-in student can add a review");
      navigate('/');
    } else {
      fetchAcceptedApplications();
    }
  }, [navigate]);

  return (
    <div className="add-review-page">
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>{error}</p>
      ) : (
        <div className="applications-list">
          {applications.length === 0 ? (
            <p>No accepted applications found.</p>
          ) : (
            applications.map((application) => (
              <div className="application-card" key={application.collegeId}>
                <h3>{application.collegeName}</h3>
                <p>Status: {application.applicationStatus}</p>
                <div className="action-buttons">
                  <button onClick={() => navigate(`/reviews/${application.collegeId}`)}>See Reviews</button>
                  <button onClick={() => navigate(`/add-review/${application.collegeId}`)}>Add New Review</button>
                </div>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
};

export default AddReviewPage;
