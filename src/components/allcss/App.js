import React from 'react';
import { Routes, Route } from 'react-router-dom'; // Import Routes and Route for routing
import Footer from './components/Footer'; // import footer
import HomePage from './components/HomePage'; // import home
import Navbar from './components/Navbar'; // import navbar
import Login from './components/Login'; // import Login component
import Register from './components/Register'; // import Register component
import Colleges from './components/Colleges';
import Courses from './components/Courses';
import Applications from './components/Applications';
import Profile from './components/Profile';
import UpdateCollege from './components/UpdateCollege';
import AddCollege from './components/AddCollege';
import AddCourse from './components/AddCourse';
import UpdateProfile from './components/UpdateProfile';
import AddReviewPage from './components/AddReviewPage';
import SeeReviewPage from './components/SeeReviewPage';
import AddReviewForm from './components/AddReviewForm';
import TermsAndConditions from './components/TermsAndConditions';
import ContactUs from './components/ContactUs';
import PrivacyPolicy from './components/PrivacyPolicy';
import UpdateCourse from './components/UpdateCourse';
const App = () => {
  return (
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />  {/* HomePage at root path */}
        <Route path="/login" element={<Login />} />  {/* Login page at '/login' */}
        <Route path="/register" element={<Register />} />  {/* Register page at '/register' */}
        <Route path="/colleges" element={<Colleges />} /> 
        <Route path="/courses" element={<Courses />} /> 
        <Route path="/applications" element={<Applications />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/update/:collegeId" element={<UpdateCollege />} />
        <Route path="/add-college" element={<AddCollege />} />
        <Route path="/add-course" element={<AddCourse />} />
        <Route path="/updateprofile/:id" element={<UpdateProfile />} />
        <Route path="/review" element={<AddReviewPage />} />
        <Route path="/reviews/:collegeId" element={<SeeReviewPage />} />
        <Route path="/add-review/:collegeId" element={<AddReviewForm />} />
        <Route path="/terms-conditions" element={<TermsAndConditions />} />
        <Route path="/contact-us" element={<ContactUs />} />
        <Route path="/privacy-policy" element={<PrivacyPolicy />} />
        <Route path="/update-course/:courseId" element={<UpdateCourse />} />
      </Routes>
      <Footer />    {/* Add footer here */}
    </div>
  );
};

export default App;
