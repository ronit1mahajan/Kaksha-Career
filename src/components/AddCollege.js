import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./allcss/AddCollege.css";

const AddCollege = () => {
  const navigate = useNavigate();

  const [college, setCollege] = useState({
    name: "",
    location: "",
    contactInfo: "",
    establishedYear: "",
    affiliatedUniversity: "",
    type: "",
  });

  const [errors, setErrors] = useState({});

  const validateForm = () => {
    const newErrors = {};
    if (!college.name.trim()) newErrors.name = "College name is required.";
    if (!college.location.trim()) newErrors.location = "Location is required.";
    if (!/^\d{10}$/.test(college.contactInfo.trim()))
      newErrors.contactInfo = "Enter a valid 10-digit contact number.";
    const year = Number(college.establishedYear);
    if (!year || year < 1800 || year > new Date().getFullYear())
      newErrors.establishedYear = "Enter a valid year between 1800 and the current year.";
    if (!college.affiliatedUniversity.trim()) newErrors.affiliatedUniversity = "Affiliated university is required.";
    if (!college.type.trim()) newErrors.type = "College type is required.";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    setCollege({ ...college, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const token = localStorage.getItem("authToken"); // Get JWT token from localStorage

      await axios.post("http://localhost:7070/colleges/add", college, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // Include JWT token
        },
      });

      alert("College added successfully!");
      navigate("/colleges");
    } catch (error) {
      console.error("Error adding college:", error);
      alert("Failed to add college. Please try again.");
    }
  };

  return (
    <div className="add-college-container">
      <h2>Add New College</h2>
      <form onSubmit={handleSubmit} className="add-college-form">
        <div className="form-group">
          <label>College Name</label>
          <input type="text" name="name" value={college.name} onChange={handleChange} />
          {errors.name && <span className="error">{errors.name}</span>}
        </div>

        <div className="form-group">
          <label>Location</label>
          <input type="text" name="location" value={college.location} onChange={handleChange} />
          {errors.location && <span className="error">{errors.location}</span>}
        </div>

        <div className="form-group">
          <label>Contact Info</label>
          <input type="text" name="contactInfo" value={college.contactInfo} onChange={handleChange} />
          {errors.contactInfo && <span className="error">{errors.contactInfo}</span>}
        </div>

        <div className="form-group">
          <label>Established Year</label>
          <input type="number" name="establishedYear" value={college.establishedYear} onChange={handleChange} />
          {errors.establishedYear && <span className="error">{errors.establishedYear}</span>}
        </div>

        <div className="form-group">
          <label>Affiliated University</label>
          <input type="text" name="affiliatedUniversity" value={college.affiliatedUniversity} onChange={handleChange} />
          {errors.affiliatedUniversity && <span className="error">{errors.affiliatedUniversity}</span>}
        </div>

        <div className="form-group">
          <label>Type</label>
          <input type="text" name="type" value={college.type} onChange={handleChange} />
          {errors.type && <span className="error">{errors.type}</span>}
        </div>

        <button type="submit" disabled={Object.keys(errors).length > 0}>
          Add College
        </button>
      </form>
    </div>
  );
};

export default AddCollege;
