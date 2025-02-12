import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./allcss/PrivacyPolicy.css"

const PrivacyPolicy = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("authToken"); // Check if user is logged in
        if (!token) {
            navigate("/login"); // Redirect to login if not authenticated
        }
    }, [navigate]);

    return (
        <div className="privacy-container">
            <h2 className="title">Privacy Policy</h2>
            <p>
                Your privacy is important to us. This Privacy Policy explains how we collect, use, and protect your information.
            </p>

            <h3>1. Information We Collect</h3>
            <p>We collect personal information such as name, email, and contact details when you register on our platform.</p>

            <h3>2. How We Use Your Information</h3>
            <p>We use your data to provide personalized services, improve our platform, and send important updates.</p>

            <h3>3. Data Protection</h3>
            <p>Your information is securely stored, and we take appropriate measures to prevent unauthorized access.</p>

            <h3>4. Third-Party Services</h3>
            <p>We do not share your personal data with third parties without your consent.</p>

            <h3>5. Changes to This Policy</h3>
            <p>We may update this Privacy Policy from time to time. Please review it periodically.</p>

            <p>If you have any questions, feel free to <strong>contact us</strong>.</p>
        </div>
    );
};

export default PrivacyPolicy;
