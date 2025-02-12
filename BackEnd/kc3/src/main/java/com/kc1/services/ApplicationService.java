package com.kc1.services;

import java.util.List;

import com.kc1.dto.AppliDTO;
import com.kc1.dto.ApplicationDTO;
import com.kc1.dto.ApplicationResponseDto;
import com.kc1.entities.Application;
import com.kc1.entities.Application.ApplicationStatus;

public interface ApplicationService {

	AppliDTO submitApplication(int userId, int collegeId, int courseId);

	List<ApplicationResponseDto> getApplicationsByUser(int userId);

	List<Application> getApplicationsByCollege(int collegeId);

	List<Application> getApplicationsByCourse(int courseId);

	Application updateApplicationStatus(Integer applicationId, ApplicationStatus applicationStatus);

	List<ApplicationResponseDto> getPendingApplications();

	List<ApplicationDTO> getAcceptedApplicationsByStudent(Integer userId);
}
