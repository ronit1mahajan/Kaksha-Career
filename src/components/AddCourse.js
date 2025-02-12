import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./allcss/AddCourse.css";

const AddCourse = () => {
  const navigate = useNavigate();

  const [course, setCourse] = useState({
    name: "",
    duration: "",
    fee: "",
    eligibilityCriteria: "",
    collegeId: "",
  });

  const [colleges, setColleges] = useState([]);
  const [errors, setErrors] = useState({});
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState("GUEST");

  useEffect(() => {
    const authToken = localStorage.getItem("authToken");
    const storedRole = localStorage.getItem("userRole") || "GUEST";

    setIsLoggedIn(!!authToken);
    setUserRole(storedRole);

    const fetchColleges = async () => {
      try {
        const response = await axios.get("http://localhost:7070/colleges/all", {
          headers: {
            Authorization: `Bearer ${authToken}`,
          },
        });
        setColleges(response.data);
      } catch (error) {
        console.error("Error fetching colleges:", error);
      }
    };

    if (authToken) {
      fetchColleges();
    }
  }, []);

  const validateForm = () => {
    const newErrors = {};
    if (!course.name.trim()) newErrors.name = "Course name is required.";
    if (!course.duration.trim()) newErrors.duration = "Duration is required.";
    if (!course.fee || isNaN(course.fee) || course.fee <= 0)
      newErrors.fee = "Enter a valid fee amount.";
    if (!course.eligibilityCriteria.trim())
      newErrors.eligibilityCriteria = "Eligibility criteria is required.";
    if (!course.collegeId.trim()) newErrors.collegeId = "College ID is required.";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    setCourse({ ...course, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const authToken = localStorage.getItem("authToken");
      await axios.post("http://localhost:7070/courses/add", course, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authToken}`,
        },
      });
      alert("Course added successfully!");
      navigate("/courses");
    } catch (error) {
      console.error("Error adding course:", error);
      alert("Failed to add course. Please check your login status.");
    }
  };

  if (!isLoggedIn || userRole !== "ADMIN") {
    return <div>You need to be logged in as an ADMIN to add courses.</div>;
  }

  return (
    <div className="add-course-container">
      <h2>Add Course</h2>
      <form onSubmit={handleSubmit} className="add-course-form">
        <div className="form-group">
          <label>Course Name</label>
          <input
            type="text"
            name="name"
            value={course.name}
            onChange={handleChange}
          />
          {errors.name && <span className="error">{errors.name}</span>}
        </div>

        <div className="form-group">
          <label>Duration</label>
          <input
            type="text"
            name="duration"
            value={course.duration}
            onChange={handleChange}
          />
          {errors.duration && <span className="error">{errors.duration}</span>}
        </div>

        <div className="form-group">
          <label>Fee</label>
          <input
            type="number"
            name="fee"
            value={course.fee}
            onChange={handleChange}
          />
          {errors.fee && <span className="error">{errors.fee}</span>}
        </div>

        <div className="form-group">
          <label>Eligibility Criteria</label>
          <textarea
            name="eligibilityCriteria"
            value={course.eligibilityCriteria}
            onChange={handleChange}
          ></textarea>
          {errors.eligibilityCriteria && (
            <span className="error">{errors.eligibilityCriteria}</span>
          )}
        </div>

        <div className="form-group">
          <label>College</label>
          <select
            name="collegeId"
            value={course.collegeId}
            onChange={handleChange}
          >
            <option value="">Select a College</option>
            {colleges.map((college) => (
              <option key={college.collegeId} value={college.collegeId}>
                {college.name}
              </option>
            ))}
          </select>
          {errors.collegeId && <span className="error">{errors.collegeId}</span>}
        </div>

        <button type="submit" disabled={Object.keys(errors).length > 0}>
          Add Course
        </button>
      </form>
    </div>
  );
};

export default AddCourse;