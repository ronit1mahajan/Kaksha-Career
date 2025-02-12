import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./allcss/TermsAndConditions.css"

const TermsAndConditions = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("authToken"); // Check if user is logged in
        if (!token) {
            navigate("/login"); // Redirect to login if not authenticated
        }
    }, [navigate]);

    return (
        <div className="terms-container">
            <h2 className="title">Terms and Conditions</h2>
            <p>
                Welcome to Kaksha Career! By using this website, you agree to the following terms:
            </p>
            <ul>
                <li>Users must provide accurate information.</li>
                <li>We are not responsible for third-party content.</li>
                <li>Do not misuse the platform for fraudulent activities.</li>
                <li>Violating the terms may result in account suspension.</li>
            </ul>
            <p>
                These terms are subject to change at any time. Please review them periodically.
            </p>
        </div>
    );
};

export default TermsAndConditions;
