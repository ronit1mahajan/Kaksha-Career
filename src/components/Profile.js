import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './allcss/Profile.css';

const Profile = () => {  
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [confirmDelete, setConfirmDelete] = useState(false);
  const navigate = useNavigate();

  const user = JSON.parse(localStorage.getItem('user'));
  const userId = user?.userId;
  const userRole = user?.role;

  useEffect(() => {
    if (!userId || !localStorage.getItem('authToken')) {
      navigate('/login');
      return;
    }

    const fetchUserProfile = async () => {
      try {
        const token = localStorage.getItem('authToken');
        const response = await axios.get(`http://localhost:7070/users/${userId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        setUserData(response.data);
      } catch (err) {
        setError('Error fetching profile data');
      } finally {
        setLoading(false);
      }
    };

    fetchUserProfile();
  }, [userId, navigate]);

  const handleDelete = async () => {
    if (userRole === 'ADMIN') {
      alert('Admins cannot delete their account');
      return;
    }

    if (!confirmDelete) {
      setConfirmDelete(true);
      return;
    }

    try {
      const token = localStorage.getItem('authToken');
      await axios.delete(`http://localhost:7070/users/delete/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      // Clear user session and redirect manually
      localStorage.removeItem('user');
      localStorage.removeItem('authToken');
      setUserData(null);
      setLoading(true);

      // Force a full page reload to ensure complete logout
      window.location.href = '/login';
    } catch (error) {
      console.error('Error deleting account:', error);
      alert('Failed to delete account. Please try again.');
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error || !userData) {
    return <div>Error: No user is logged in.</div>;
  }

  return (
    <div className="profile-container">
      <div className="profile-box">
        <h2>User Profile</h2>
        <div>
          <p><strong>Name:</strong> {userData.name}</p>
          <p><strong>Email:</strong> {userData.email}</p>
          <p><strong>Role:</strong> {userData.role}</p>
          <p><strong>Date of Registration:</strong> {new Date(userData.dateOfRegistration).toLocaleDateString()}</p>
        </div>
        
        <button onClick={() => navigate(`/updateprofile/${userId}`)} className="update-btn">
          Update Profile
        </button>

        <button onClick={handleDelete} className="delete-btn">
          {confirmDelete ? "Are you sure? Click again to confirm" : "Delete Account"}
        </button>
      </div>
    </div>
  );
};

export default Profile;
