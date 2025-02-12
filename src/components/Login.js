import React, { useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./allcss/Login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [captcha, setCaptcha] = useState(generateCaptcha());
  const [userCaptcha, setUserCaptcha] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Check if the user is already logged in on component mount
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      navigate("/"); // Redirect to home page if logged in
    }
  }, [navigate]);

  function generateCaptcha() {
    return Math.floor(1000 + Math.random() * 9000).toString(); // Generates a 4-digit random number
  }

  const handleLogin = async (e) => {
    e.preventDefault();
  
    if (!email || !password) {
      setError("Please enter both email and password.");
      return;
    }
  
    if (userCaptcha !== captcha) {
      setError("Captcha does not match! Try again.");
      setCaptcha(generateCaptcha()); // Refresh captcha on failure
      return;
    }
  
    try {
      const response = await axios.post("http://localhost:7070/users/login", { email, password });
  
      if (response.status === 201) {
        const token = response.data.jwt;
  
        if (token) {
          console.log("Received Token:", token); // Debugging line
  
          localStorage.setItem("authToken", token);
          localStorage.setItem("userName", response.data.name);
          localStorage.setItem("userRole", response.data.role);
          localStorage.setItem("user", JSON.stringify(response.data));
  
          console.log("Stored Token:", localStorage.getItem("authToken")); // Debugging line
          // Reload the entire page to reflect changes in navbar
        window.location.reload();
          navigate("/"); // Redirect to home
        } else {
          setError("Token is missing in response. Login failed.");
        }
      }
    } catch (error) {
      setError("Invalid email or password. Try again!");
    }
  };
  

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>
        {error && <p className="error">{error}</p>}
        <form onSubmit={handleLogin}>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <div className="captcha-box">
            <p className="captcha-text">{captcha}</p>
            <input
              type="text"
              placeholder="Enter captcha"
              value={userCaptcha}
              onChange={(e) => setUserCaptcha(e.target.value)}
              required
            />
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;