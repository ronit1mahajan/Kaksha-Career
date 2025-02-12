import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useSearchParams } from "react-router-dom";
import "./allcss/Courses.css";

const Courses = () => {
  const [courses, setCourses] = useState([]);
  const [filteredCourses, setFilteredCourses] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [coursesPerPage] = useState(10);
  const [error, setError] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState("GUEST");
  const [searchTerm, setSearchTerm] = useState("");
  const navigate = useNavigate();
  const [searchParams] = useSearchParams(); // Hook to access URL query params

  useEffect(() => {
    // Check if user is logged in
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser);
        if (user?.userId) {
          setIsLoggedIn(true);
          setUserRole(localStorage.getItem("userRole") || "GUEST");
        }
      } catch (error) {
        console.error("Error parsing user data:", error);
      }
    }

    const searchQuery = searchParams.get("search"); // Get search parameter from URL

    // Fetch courses
    const fetchCourses = async () => {
      try {
        const response = await axios.get("http://localhost:7070/courses/all");
        setCourses(response.data);
        if (searchQuery) {
          // Filter courses based on course name or college name
          const filtered = response.data.filter(course =>
            course.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
            course.collegeName.toLowerCase().includes(searchQuery.toLowerCase())
          );
          setFilteredCourses(filtered);
          setSearchTerm(searchQuery); // Set search term from query in input field
        } else {
          setFilteredCourses(response.data); // If no query, display all courses
        }
      } catch (error) {
        console.error("Error fetching courses:", error);
      }
    };

    fetchCourses();
  }, [searchParams]); // Run the effect when the search params change

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
    const filtered = courses.filter((course) =>
      course.name.toLowerCase().includes(e.target.value.toLowerCase()) ||
      course.collegeName.toLowerCase().includes(e.target.value.toLowerCase())
    );
    setFilteredCourses(filtered);

    // Update the URL with the search term
    const searchParams = new URLSearchParams(window.location.search);
    searchParams.set("search", e.target.value);
    navigate(`${window.location.pathname}?${searchParams.toString()}`, { replace: true });
  };

  const indexOfLastCourse = currentPage * coursesPerPage;
  const indexOfFirstCourse = indexOfLastCourse - coursesPerPage;
  const currentCourses = filteredCourses.slice(indexOfFirstCourse, indexOfLastCourse);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  const handleApply = async (course) => {
    const userData = localStorage.getItem("user");
    
    if (!userData) {
      alert("You must be logged in to apply.");
      return;
    }
  
    let user;
    try {
      user = JSON.parse(userData);
    } catch (error) {
      console.error("Invalid user data in localStorage:", error);
      alert("Login data is corrupted. Please log in again.");
      return;
    }
  
    if (!user.userId) {
      alert("User ID missing. Please log in again.");
      return;
    }
  
    if (!course.courseId || !course.collegeId) {
      console.error("Invalid course data:", course);
      alert("Course data is incomplete. Please try again later.");
      return;
    }
  
    const appDto = {
      userId: user.userId,
      courseId: course.courseId,
      collegeId: course.collegeId,
    };
  
    try {
      const token = localStorage.getItem("authToken");
      if (!token) {
        setError("Token is missing. Please log in again.");
        return;
      }
      const response = await axios.post("http://localhost:7070/applications/apply", appDto, 
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
  
      if (response.status === 201) {
        alert(`Application submitted successfully for ${course.name}`);
      } else {
        alert("Error submitting application.");
      }
    } catch (error) {
      console.error("Error applying for course:", error.response ? error.response.data : error.message);
      alert(`Failed to apply: ${error.response?.data?.message || "Server error"}`);
    }
  };

  const handleAddCourse = () => {
    if (userRole === "ADMIN") {
      navigate("/add-course");
    } else {
      alert("You must be an admin to add courses.");
    }
  };

  const handleDeleteCourse = async (courseId) => {
    if (window.confirm("Are you sure you want to delete this course?")) {
      try {
        const token = localStorage.getItem("authToken"); // Ensure the correct key is used for JWT
        if (!token) {
          setError("Token is missing. Please log in again.");
          return;
        }
        await axios.delete(`http://localhost:7070/courses/delete/${courseId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        });
        setCourses(courses.filter((course) => course.courseId !== courseId));
        setFilteredCourses(filteredCourses.filter((course) => course.courseId !== courseId));
      } catch (error) {
        console.error("Error deleting course:", error);
      }
    }
  };

  const handleUpdateCourse = (courseId) => {
    if (userRole === "ADMIN") {
      navigate(`/update-course/${courseId}`);
    } else {
      alert("You must be an admin to update courses.");
    }
  };

  return (
    <div className="courses-container">
      <div className="courses-header">
        <h2>Courses</h2>&nbsp; &nbsp; &nbsp; &nbsp;
        <input
          type="text"
          placeholder="Search Courses or College..."
          value={searchTerm}
          onChange={handleSearchChange}
        />&nbsp; &nbsp; &nbsp; &nbsp;
        {isLoggedIn && userRole === "ADMIN" && (
          <button onClick={handleAddCourse} className="add-course-button">
            Add Course to College
          </button>
        )}
      </div>

      <div className="courses-list">
        {currentCourses.length === 0 ? (
          <p>No courses available</p>
        ) : (
          currentCourses.map((course) => (
            <div className="course-card" key={course.courseId}>
              <h3>{course.name}</h3>
              <p><strong>Duration:</strong> {course.duration}</p>
              <p><strong>Fees:</strong> ₹{course.fee}</p>
              <p><strong>College:</strong> {course.collegeName}</p>
              <p><strong>Location:</strong> {course.collegeLocation}</p>
              {isLoggedIn && userRole === "STUDENT" && (
                <button onClick={() => handleApply(course)}>Apply for Course</button>
              )}
              {isLoggedIn && userRole === "ADMIN" && (
                <>
                  <button onClick={() => handleUpdateCourse(course.courseId)} className="update-button">
                    Update Course
                  </button>
                  <button onClick={() => handleDeleteCourse(course.courseId)} className="delete-button">
                    Delete Course
                  </button>
                </>
              )}
            </div>
          ))
        )}
      </div>

      <div className="pagination">
        {filteredCourses.length > coursesPerPage &&
          Array.from({ length: Math.ceil(filteredCourses.length / coursesPerPage) }).map((_, index) => (
            <button key={index} onClick={() => paginate(index + 1)}>
              {index + 1}
            </button>
          ))}
      </div>
    </div>
  );
};

export default Courses;
