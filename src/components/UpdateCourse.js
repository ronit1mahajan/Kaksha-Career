import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./allcss/UpdateCourse.css";

const UpdateCourse = () => {
  const { courseId } = useParams();
  const navigate = useNavigate();

  const [course, setCourse] = useState({
    courseId: "",
    name: "",
    duration: "",
    fee: "",
    eligibilityCriteria: "",
    collegeName: "",
    collegeLocation: "",
    collegeId: "",
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    const fetchCourse = async () => {
      try {
        const token = localStorage.getItem("authToken");
        const response = await axios.get(`http://localhost:7070/courses/get/${courseId}`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setCourse(response.data);
      } catch (error) {
        console.error("Error fetching course:", error);
      }
    };
    fetchCourse();
  }, [courseId]);

  const validateForm = () => {
    const newErrors = {};
    if (!course.name.trim()) newErrors.name = "Course name is required.";
    if (!course.duration.trim()) newErrors.duration = "Duration is required.";
    if (!course.fee || isNaN(course.fee) || course.fee <= 0) newErrors.fee = "Enter a valid course fee.";
    if (!course.eligibilityCriteria.trim()) newErrors.eligibilityCriteria = "Eligibility criteria is required.";
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
      const token = localStorage.getItem("authToken");
      await axios.put(
        `http://localhost:7070/courses/update/${courseId}`,
        {
          name: course.name,
          duration: course.duration,
          fee: course.fee,
          eligibilityCriteria: course.eligibilityCriteria,
        },
        {
          withCredentials: true,
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      navigate("/courses");
    } catch (error) {
      console.error("Error updating course:", error);
      alert("Failed to update course. Please check your login status.");
    }
  };

  return (
    <div className="update-course-container">
      <h2>Update Course Details</h2>
      <form onSubmit={handleSubmit} className="update-course-form">
        <div className="form-group">
          <label>Course ID (Immutable)</label>
          <input type="text" value={courseId} disabled />
        </div>

        <div className="form-group">
          <label>Course Name</label>
          <input type="text" name="name" value={course.name} onChange={handleChange} />
          {errors.name && <span className="error">{errors.name}</span>}
        </div>

        <div className="form-group">
          <label>Duration</label>
          <input type="text" name="duration" value={course.duration} onChange={handleChange} />
          {errors.duration && <span className="error">{errors.duration}</span>}
        </div>

        <div className="form-group">
          <label>Course Fee</label>
          <input type="number" name="fee" value={course.fee} onChange={handleChange} />
          {errors.fee && <span className="error">{errors.fee}</span>}
        </div>

        <div className="form-group">
          <label>Eligibility Criteria</label>
          <input type="text" name="eligibilityCriteria" value={course.eligibilityCriteria} onChange={handleChange} />
          {errors.eligibilityCriteria && <span className="error">{errors.eligibilityCriteria}</span>}
        </div>

        <button type="submit" disabled={Object.keys(errors).length > 0}>
          Update Course
        </button>
      </form>
    </div>
  );
};

export default UpdateCourse;
