import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './allcss/HomePage.css';
import axios from 'axios';

const HomePage = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userName, setUserName] = useState('Guest');
  const [userRole, setUserRole] = useState(''); // State to store user role
  const [collegeName, setCollegeName] = useState('');
  const [courseName, setCourseName] = useState('');
  const [colleges, setColleges] = useState([]); // State for storing colleges
  const [loading, setLoading] = useState(false); // Loading state for search
  const [error, setError] = useState(null); // Error state for search
  const [currentPage, setCurrentPage] = useState(1);
  const [collegesPerPage] = useState(10);
  const navigate = useNavigate();

  useEffect(() => {
    const userToken = localStorage.getItem('authToken');
    const user = localStorage.getItem('userName');
    const role = localStorage.getItem('userRole'); // Assuming role is stored in localStorage
    if (userToken) {
      setIsLoggedIn(true);
      setUserName(user || 'User');
      setUserRole(role || ''); // Set the user role
    }
  }, []);

  const handleCollegeSearch = () => {
    if (collegeName.trim() !== '') {
      setLoading(true);  // Start loading
      setError(null);    // Reset any previous errors
      navigate(`/colleges?search=${encodeURIComponent(collegeName)}`);
    }
  };

  const handleCourseSearch = () => {
    if (courseName.trim() !== '') {
      setLoading(true);  // Start loading
      setError(null);    // Reset any previous errors
      navigate(`/courses?search=${encodeURIComponent(courseName)}`);
    }
  };

  // Pagination logic
  const indexOfLastCollege = currentPage * collegesPerPage;
  const indexOfFirstCollege = indexOfLastCollege - collegesPerPage;
  const currentColleges = colleges.slice(indexOfFirstCollege, indexOfLastCollege);

  return (
    <div className="home-page">
      {/* Welcome Box */}
      <div className="welcome-box">
        <h1>Welcome, {isLoggedIn ? userName : 'Guest'}!</h1>
        {!isLoggedIn && <p>Please log in to get the full experience!</p>}

        {/* Typing Animation */}
        <h2 className="typing-text">Find Colleges All Over India</h2>
        <br />

        {/* College Search */}
        <input
          type="text"
          value={collegeName}
          onChange={(e) => setCollegeName(e.target.value)}
          placeholder="Enter college name"
        />
        <button onClick={handleCollegeSearch} disabled={!collegeName.trim() || loading}>
          {loading ? 'Searching...' : 'Search'}
        </button>

        {/* Display Error if Any */}
        {error && <p className="error-message">{error}</p>}

        {/* Display Search Results */}
        {colleges.length > 0 && (
          <div className="search-results">
            <h3>Search Results:</h3>
            <ul>
              {currentColleges.map((college, index) => (
                <li key={index}>{college.name}</li>
              ))}
            </ul>
            <div className="pagination">
              {colleges.length > collegesPerPage &&
                Array.from({ length: Math.ceil(colleges.length / collegesPerPage) }).map((_, index) => (
                  <button key={index} onClick={() => setCurrentPage(index + 1)}>
                    {index + 1}
                  </button>
                ))}
            </div>
          </div>
        )}
      </div>

      {/* Course Search (Outside Box) */}
      <div className="search-course">
        <h2>Find Courses</h2>
        <br />
        <input
          type="text"
          value={courseName}
          onChange={(e) => setCourseName(e.target.value)}
          placeholder="Enter course name"
        />
        <button onClick={handleCourseSearch} disabled={!courseName.trim() || loading}>
          {loading ? 'Searching...' : 'Search'}
        </button>
      </div>

      {/* Add Review Button (Only if user is not admin) */}
      {userRole !== 'ADMIN' && (
        <div className="add-review">
          <button
            className="cool-button"
            onClick={() => isLoggedIn ? navigate('/review') : alert('Please log in to add a review!')}
          >
            Add Review
          </button>
        </div>
      )}

      {/* Study Goals Section */}
      <div className="study-goals-section">
        <h3>Select Your Study Goal</h3>
        <div className="study-goals">
          <div className="goal-card" onClick={() => navigate('/colleges?type=Engineering')}>
            <h4>⚙️ Engineering</h4>
            <p>20 Colleges</p>
          </div>
          <div className="goal-card" onClick={() => navigate('/colleges?type=Management')}>
            <h4>📊 Management</h4>
            <p>23 Colleges</p>
          </div>
          <div className="goal-card" onClick={() => navigate('/colleges?type=Commerce')}>
            <h4>📚 Commerce</h4>
            <p>25 Colleges</p>
          </div>
          <div className="goal-card" onClick={() => navigate('/colleges?type=Arts')}>
            <h4>🎨 Arts</h4>
            <p>20 Colleges</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
