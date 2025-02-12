import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
import "./allcss/Colleges.css";

const Colleges = () => {
  const [colleges, setColleges] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [collegesPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [error, setError] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState("GUEST");

  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const userToken = localStorage.getItem("authToken");
    const storedRole = localStorage.getItem("userRole") || "GUEST";

    setIsLoggedIn(!!userToken);
    setUserRole(storedRole);

    const searchParams = new URLSearchParams(location.search);
    const searchQuery = searchParams.get("search") || "";
    setSearchTerm(searchQuery);

    const fetchColleges = async () => {
      try {
        const response = await axios.get("http://localhost:7070/colleges/all");
        const type = searchParams.get("type");

        let filteredData = response.data;
        if (type) {
          filteredData = filteredData.filter((college) => college.type === type);
        }

        setColleges(filteredData);
      } catch (error) {
        console.error("Error fetching colleges:", error);
        setError("Error fetching colleges. Please try again later.");
      }
    };

    fetchColleges();
  }, [location.search]);

  const handleSearchChange = (e) => {
    const searchValue = e.target.value.toLowerCase();
    setSearchTerm(searchValue);
    setCurrentPage(1);

    const searchParams = new URLSearchParams(location.search);
    searchParams.set("search", searchValue);

    navigate(`${location.pathname}?${searchParams.toString()}`, { replace: true });
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this college?")) {
      try {
        const token = localStorage.getItem("authToken");
        if (!token) {
          setError("Token is missing. Please log in again.");
          return;
        }

        // Use the token for authorization in the header
        await axios.delete(`http://localhost:7070/colleges/delete/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        // Filter out the deleted college from the list
        setColleges((prevColleges) =>
          prevColleges.filter((college) => college.collegeId !== id)
        );
      } catch (error) {
        if (error.response) {
          if (error.response.status === 403) {
            setError("You do not have permission to delete this college.");
          } else if (error.response.status === 401) {
            setError("Session expired. Please log in again.");
          } else {
            setError("Error deleting college. Please try again.");
          }
        } else {
          setError("Error deleting college. Please try again.");
        }
      }
    }
  };

  const handleViewCourses = (collegeName) => {
    navigate(`/courses?search=${collegeName}`);
  };

  const handleViewReviews = (collegeId) => {
    navigate(`/reviews/${collegeId}`);
  };

  const filteredColleges = colleges.filter(
    (college) =>
      college.name.toLowerCase().includes(searchTerm) ||
      college.location.toLowerCase().includes(searchTerm)
  );

  const indexOfLastCollege = currentPage * collegesPerPage;
  const indexOfFirstCollege = indexOfLastCollege - collegesPerPage;
  const currentColleges = filteredColleges.slice(indexOfFirstCollege, indexOfLastCollege);

  return (
    <div className="colleges-container">
      <div className="colleges-header">
        <h2>Colleges</h2>&nbsp; &nbsp; &nbsp; &nbsp;
        <input
          type="text"
          placeholder="Search Colleges..."
          value={searchTerm}
          onChange={handleSearchChange}
        />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        {isLoggedIn && userRole === "ADMIN" && (
          <button className="add-college-btn" onClick={() => navigate("/add-college")}>Add College</button>
        )}
      </div>

      {error && <div className="error-message">{error}</div>}

      <div className="colleges-list">
        {currentColleges.length === 0 ? (
          <p>No colleges found</p>
        ) : (
          currentColleges.map((college) => (
            <div className="college-card" key={college.collegeId}>
              <h3>{college.name}</h3>
              <p><strong>Location:</strong> {college.location}</p>
              <p><strong>Type:</strong> {college.type}</p>
              <p><strong>Contact Info:</strong> {college.contactInfo}</p>
              {isLoggedIn && userRole === "STUDENT" && (
                <button onClick={() => handleViewCourses(college.name)}>
                  View Courses
                </button>
              )}
              {isLoggedIn && userRole !== "ADMIN" && (
                <button onClick={() => handleViewReviews(college.collegeId)}>
                  View Reviews
                </button>
              )}
              {isLoggedIn && userRole === "ADMIN" && (
                <>
                  <button onClick={() => navigate(`/update/${college.collegeId}`)}>Update</button>
                  <button onClick={() => handleDelete(college.collegeId)}>Delete</button>
                </>
              )}
            </div>
          ))
        )}
      </div>

      <div className="pagination">
        {filteredColleges.length > collegesPerPage &&
          Array.from({ length: Math.ceil(filteredColleges.length / collegesPerPage) }).map((_, index) => (
            <button
              key={index}
              onClick={() => setCurrentPage(index + 1)}
              className="pagination-btn"
            >
              {index + 1}
            </button>
          ))}
      </div>
    </div>
  );
};

export default Colleges;
