import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./allcss/Applications.css";

const Applications = ({ user }) => {
  const [applications, setApplications] = useState([]);
  const navigate = useNavigate();
  const userData = JSON.parse(localStorage.getItem("user"));
  const userId = userData ? userData.userId : null;
  const userRole = localStorage.getItem("userRole");

  // Fetch Applications function
  const fetchApplications = async () => {
    try {
      let response;
      const headers = {
        Authorization: `Bearer ${localStorage.getItem("authToken")}`, // JWT token in the header
      };

      if (userRole === "ADMIN") {
        response = await axios.get("http://localhost:7070/applications/pending", { headers });
      } else if (userId) {
        response = await axios.get(`http://localhost:7070/applications/user/${userId}`, { headers });
      }
      setApplications(response.data);
    } catch (error) {
      console.error("Error fetching applications:", error);
    }
  };

  // Initial load of applications
  useEffect(() => {
    if (userId) {
      fetchApplications();
    }
  }, [userId]);

  const updateApplicationStatus = async (applicationId, applicationStatus) => {
    try {
      const headers = {
        Authorization: `Bearer ${localStorage.getItem("authToken")}`, // JWT token in the header
      };

      const response = await axios.put(
        `http://localhost:7070/applications/${applicationId}/applicationStatus`,
        { applicationStatus },
        { headers }
      );

      if (response.status === 200) {
        // After successful update, re-fetch the applications
        fetchApplications();
      }
    } catch (error) {
      console.error("Error updating application status:", error);
    }
  };

  return (
    <div className="applications-container">
      <h2>{userRole === "ADMIN" ? "Pending Applications" : "Your Applications"}</h2>

      {applications.length === 0 ? (
        <p>No applications found.</p>
      ) : (
        <div className="applications-list">
          {applications.map((application) => (
            <div className="application-card" key={application.applicationId}>
              <h3>Application ID: {application.applicationId}</h3>
              {userRole === "ADMIN" ? (
                <>
                  <p><strong>Username:</strong> {application.username}</p>
                  <p><strong>User ID:</strong> {application.userId}</p>
                  <p><strong>User Name:</strong> {application.userName}</p>
                  <p><strong>College Name:</strong> {application.collegeName}</p>
                  <p><strong>Course Name:</strong> {application.courseName}</p>
                  <button onClick={() => updateApplicationStatus(application.applicationId, "ACCEPTED")}>
                    Accept
                  </button>
                  <button onClick={() => updateApplicationStatus(application.applicationId, "REJECTED")}>
                    Reject
                  </button>
                </>
              ) : (
                <>
                  <p><strong>College Name:</strong> {application.collegeName}</p>
                  <p><strong>Course Name:</strong> {application.courseName}</p>
                  <p><strong>Status:</strong> {application.applicationStatus}</p>
                </>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Applications;
