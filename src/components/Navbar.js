import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import "./allcss/Navbar.css";

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate(); // Replaced useHistory with useNavigate

  useEffect(() => {
    // Check if the user is logged in by checking a token or any auth status.
    const userLoggedIn = localStorage.getItem('authToken');  // Example, adjust based on your auth method
    if (userLoggedIn) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleLogout = () => {
    // Clear user session or token
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    localStorage.removeItem('userRole');
    localStorage.removeItem('userName');
    setIsLoggedIn(false);
    navigate('/login'); // Replaced history.push with navigate
  };

  return (
    <nav className="navbar">
      <div className="logo">
        <img src="/kclogo.jpg" alt="Kaksha Career Logo" width="75" />
        <h1>Kaksha Career</h1>
      </div>
      <ul className="nav-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/colleges">Colleges</Link></li>
        <li><Link to="/courses">Courses</Link></li>
        <li><Link to="/applications">Applications</Link></li>
        {isLoggedIn ? (
          <>
            <li><Link to="/review">Add Review</Link></li>
            <li><Link to="/profile">Profile</Link></li>
            <li><button onClick={handleLogout}>Logout</button></li>
          </>
        ) : (
          <>
            <li><Link to="/login">Login</Link></li>
            <li><Link to="/register">Register</Link></li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
