import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import './allcss/UpdateProfile.css';

const UpdateProfile = () => {
  const [userData, setUserData] = useState({
    name: "",
    email: "",
    dateOfRegistration: "",
    role: "",
    userId: "",
  });
  const [password, setPassword] = useState(""); // Separate state for password
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  // Get user ID and JWT token from local storage
  const userId = JSON.parse(localStorage.getItem("user"))?.userId;
  const token = localStorage.getItem("authToken"); // JWT token

  useEffect(() => {
    if (!userId || !token) {
      setError("User not found or not authenticated. Please log in.");
      setLoading(false);
      return;
    }

    const fetchUserProfile = async () => {
      try {
        const response = await axios.get(`http://localhost:7070/users/${userId}`, {
          headers: {
            "Authorization": `Bearer ${token}`, // Pass JWT token in Authorization header
          },
        });

        setUserData({
          name: response.data.name,
          email: response.data.email,
          dateOfRegistration: response.data.dateOfRegistration,
          role: response.data.role,
          userId: response.data.userId,
        });
        setLoading(false);
      } catch (err) {
        setError("Error fetching profile data");
        setLoading(false);
      }
    };

    fetchUserProfile();
  }, [userId, token]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    // Create a copy of userData to send
    const updatedUserData = { ...userData };

    // If password field is not empty, add it to the request
    if (password.trim()) {
      updatedUserData.password = password;
    }

    try {
      await axios.put(`http://localhost:7070/users/${userId}`, updatedUserData, {
        headers: {
          "Authorization": `Bearer ${token}`, // Include the token in the headers
        },
      });
      navigate("/profile"); // Redirect after successful update
    } catch (err) {
      console.error("Error updating profile", err);
      alert("Failed to update profile. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="update-profile-container">
      <h2>Update Profile</h2>
      <form onSubmit={handleSubmit} className="update-profile-form">
        {/* Name */}
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={userData.name}
            onChange={handleChange}
            required
          />
        </div>

        {/* Email */}
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={userData.email}
            onChange={handleChange}
            required
          />
        </div>

        {/* Password */}
        <div className="form-group">
          <label htmlFor="password">Password (Leave blank to keep unchanged)</label>
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>

        {/* Date of Registration (Read-Only) */}
        <div className="form-group">
          <label htmlFor="dateOfRegistration">Date of Registration</label>
          <input
            type="date"
            id="dateOfRegistration"
            name="dateOfRegistration"
            value={userData.dateOfRegistration}
            readOnly
            disabled
          />
        </div>

        {/* Role (Read-Only) */}
        <div className="form-group">
          <label htmlFor="role">Role</label>
          <input
            type="text"
            id="role"
            name="role"
            value={userData.role}
            readOnly
            disabled
          />
        </div>

        {/* User ID (Read-Only) */}
        <div className="form-group">
          <label htmlFor="userId">User ID</label>
          <input
            type="text"
            id="userId"
            name="userId"
            value={userData.userId}
            readOnly
            disabled
          />
        </div>

        {/* Submit Button */}
        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Updating..." : "Update Profile"}
        </button>
      </form>
    </div>
  );
};

export default UpdateProfile;
