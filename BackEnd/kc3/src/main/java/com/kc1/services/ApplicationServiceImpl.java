package com.kc1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kc1.dto.AppliDTO;
import com.kc1.dto.ApplicationDTO;
import com.kc1.dto.ApplicationResponseDto;
import com.kc1.entities.Application;
import com.kc1.entities.Application.ApplicationStatus;
import com.kc1.entities.College;
import com.kc1.entities.Course;
import com.kc1.entities.Review;
import com.kc1.entities.User;
import com.kc1.exceptions.ResourceNotFoundException;
import com.kc1.repositories.ApplicationDao;
import com.kc1.repositories.CollegeDao;
import com.kc1.repositories.CourseDao;
import com.kc1.repositories.ReviewDao;
import com.kc1.repositories.UserDao;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
	
	 	@Autowired
	    private ApplicationDao applicationDao;

	    @Autowired
	    private UserDao userDao;

	    @Autowired
	    private CollegeDao collegeDao;

	    @Autowired
	    private CourseDao courseDao;
	    
	    @Autowired
	    private EmailService emailService;

	    @Override
	    public AppliDTO submitApplication(int userId, int collegeId, int courseId) {
	        User user = userDao.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	        College college = collegeDao.findById(collegeId)
	                .orElseThrow(() -> new ResourceNotFoundException("College not found"));

	        Course course = courseDao.findById(courseId)
	                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

	        if (applicationDao.findByUserIdAndCollegeId(userId, collegeId).isPresent()) {
	            throw new ResourceNotFoundException("Application already exists for this college.");
	        }

	        Application application = new Application(user, college, course, Application.ApplicationStatus.PENDING);
	        Application saved=applicationDao.save(application);
	        emailService.sendEmail(saved.getUser().getEmail(), "Application Submitted",
	                "Dear " + saved.getUser().getName() + ",\n\nYour application for " + saved.getCollege().getName()+"in course "+ saved.getCourse().getName() + " has been submitted.\n\nThanks!");

	        emailService.sendEmail("kakshacareers@gmail.com", "New Application Received",
	                "A new application has been received from " + saved.getUser().getEmail() + " for " + saved.getCollege().getName());
	        
	        AppliDTO aDto= new AppliDTO(saved.getUser().getUserId(), saved.getCourse().getCourseId(), saved.getCollege().getCollegeId(), saved.getApplicationStatus());
	        return aDto;
	    }
	    
	    @Override
	    public List<ApplicationResponseDto> getPendingApplications() {
	        List<Application> pendingApplications = applicationDao.findByApplicationStatus(ApplicationStatus.PENDING);
	        
	        return pendingApplications.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	    }
	    
	    @Override
	    public Application updateApplicationStatus(Integer applicationId, ApplicationStatus applicationStatus) {
	        
	        Optional<Application> applicationOpt = applicationDao.findById(applicationId);

	        if (applicationOpt.isPresent()) {
	            Application application = applicationOpt.get();
	            application.setApplicationStatus(applicationStatus);
	            Application saved = applicationDao.save(application);
	            
	            if(saved.getApplicationStatus().toString()=="ACCEPTED") {
	            	emailService.sendEmail(saved.getUser().getEmail(), "Application Accepted",
	                "Dear " + saved.getUser().getName() + ",\n\nYour application for " + saved.getCollege().getName()+ "in course "+ saved.getCourse().getName() + " has been Accepted.\n"
	                		+ "Fill the google form below for further Process. \n"
	                		+ "https://forms.gle/uk7MiJpRhs9WVTMS7  \n"
	                		+ "\nThank you See you soon !");
	            }
	            if(saved.getApplicationStatus().toString()=="REJECTED") {
	            	emailService.sendEmail(saved.getUser().getEmail(), "Application Rejected",
	                "Dear " + saved.getUser().getName() + ",\n\nYour application for " + saved.getCollege().getName()+ "in course "+ saved.getCourse().getName() + " has been Rejected.\n"
	                		+ "You can try for other colleges or courses.\n"
	                		+ "\nThanks !");
	            }
	            return saved;
	        } else {
	            return null; 
	        }
	    }
	    
	    @Override
	    public List<ApplicationResponseDto> getApplicationsByUser(int userId) {
	    	User user = userDao.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	        List<Application> applications = applicationDao.findByUser(user);
	        
	        return applications.stream().map(this::convertToDto).collect(Collectors.toList());
	    }
	    private ApplicationResponseDto convertToDto(Application application) {
	        return new ApplicationResponseDto(
	                application.getApplicationId(),
	                application.getUser().getUserId(),
	                application.getUser().getName(),
	                application.getCollege().getCollegeId(),
	                application.getCollege().getName(),
	                application.getCourse().getCourseId(),
	                application.getCourse().getName(),
	                application.getApplicationStatus()
	        );
	    }
	    
	    
	    @Override
	    public List<Application> getApplicationsByCollege(int collegeId) {
	        return applicationDao.findByCollegeId(collegeId);
	    }

	    @Override
	    public List<Application> getApplicationsByCourse(int courseId) {
	        return applicationDao.findByCourseId(courseId);
	    }
	    
	    @Autowired
	    private ReviewDao reviewRepository;

	    @Override
	    public List<ApplicationDTO> getAcceptedApplicationsByStudent(Integer userId) {
	        List<Application> applications = applicationDao.findByUserIdAndApplicationStatus(userId, ApplicationStatus.ACCEPTED);
	        List<ApplicationDTO> applicationDTOs = new ArrayList<>();
	        for (Application application : applications) {
	            applicationDTOs.add(new ApplicationDTO(
	                    application.getCollege().getCollegeId(),
	                    application.getCollege().getName(),
	                    application.getApplicationStatus()
	            ));
	        }
	        return applicationDTOs;
	    }

	    public List<Review> getReviewsByCollege(Integer collegeId) {
	        return reviewRepository.findByCollegeCollegeId(collegeId);
	    }

	    public Review addReview(Integer collegeId, Integer userId, String reviewText, int rating) {
	        College college = collegeDao.findById(collegeId).orElseThrow(() -> new RuntimeException("College not found"));
	        User user = new User(); 

	        Review review = new Review();
	        review.setCollege(college);
	        review.setUser(user);
	        review.setComment(reviewText);
	        review.setRating(rating);

	        return reviewRepository.save(review);
	    }

}
