import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./allcss/Contact.css"

const ContactUs = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("authToken"); // Check if user is logged in
        if (!token) {
            navigate("/login"); // Redirect to login if not authenticated
        }
    }, [navigate]);

    return (
        <div className="contact-container">
            <h2 className="title">Contact Us</h2>
            <p>If you have any questions, feel free to reach out to us.</p>

            <div className="contact-details">
                <p><strong>Email:</strong> kakshacareer@gmail.com</p>
                <p><strong>Phone:</strong> +91 9926240505</p>
                <p><strong>Address:</strong> 123, Knowledge Street, Pune, India</p>
            </div>

            <p>We are here to assist you. Feel free to contact us anytime.</p>
        </div>
    );
};

export default ContactUs;
