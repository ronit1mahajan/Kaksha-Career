import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./allcss/UpdateCollege.css";

const UpdateCollege = () => {
  const { collegeId } = useParams();
  const navigate = useNavigate();

  const [college, setCollege] = useState({
    collegeId: "",
    name: "",
    location: "",
    contactInfo: "",
    establishedYear: "",
    affiliatedUniversity: "",
    type: "",
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    const fetchCollege = async () => {
      try {
        const token = localStorage.getItem("authToken");
        const response = await axios.get(`http://localhost:7070/colleges/${collegeId}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
          }, 
        });
        setCollege(response.data);
      } catch (error) {
        console.error("Error fetching college:", error);
      }
    };
    fetchCollege();
  }, [collegeId]);

  const validateForm = () => {
    const newErrors = {};
    if (!college.name.trim()) newErrors.name = "College name is required.";
    if (!college.location.trim()) newErrors.location = "Location is required.";
    if (!college.contactInfo.trim()) newErrors.contactInfo = "Enter a valid 10-digit contact number.";
    if (!college.establishedYear || college.establishedYear < 1800 || college.establishedYear > new Date().getFullYear())
      newErrors.establishedYear = "Enter a valid year between 1800 and the current year.";
    if (!college.affiliatedUniversity.trim()) newErrors.affiliatedUniversity = "Affiliated university is required.";
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
      const token = localStorage.getItem("authToken");
      await axios.put(
        `http://localhost:7070/colleges/update/${collegeId}`,
        college,
        {
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`, 
          },
        }
      );
      navigate("/colleges");
    } catch (error) {
      console.error("Error updating college:", error);
      alert("Failed to update college. Please check your login status.");
    }
  };

  return (
    <div className="update-college-container">
      <h2>Update College Details</h2>
      <form onSubmit={handleSubmit} className="update-college-form">
        <div className="form-group">
          <label>College ID (Immutable)</label>
          <input type="text" value={collegeId} disabled />
        </div>

        <div className="form-group">
          <label>Type (Immutable)</label>
          <input type="text" value={college.type} disabled />
        </div>

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

        <button type="submit" disabled={Object.keys(errors).length > 0}>
          Update College
        </button>
      </form>
    </div>
  );
};

export default UpdateCollege;
