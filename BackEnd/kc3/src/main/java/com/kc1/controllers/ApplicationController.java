package com.kc1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kc1.dto.AddAppliDto;
import com.kc1.dto.AppliDTO;
import com.kc1.dto.ApplicationDTO;
import com.kc1.dto.ApplicationResponseDto;
import com.kc1.dto.UpdateApplicationStatusDto;
import com.kc1.entities.Application;
import com.kc1.services.ApplicationService;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<?> submitApplication(@RequestBody AddAppliDto appDto) {
        try {
        	Integer userId=appDto.getUserId();
        	Integer courseId=appDto.getCourseId();
        	Integer collegeId=appDto.getCollegeId();
            AppliDTO submittedApplication = applicationService.submitApplication(userId, collegeId, courseId);
            return new ResponseEntity<>(submittedApplication, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getApplicationsByUser(@PathVariable Integer userId) {
        List<ApplicationResponseDto> applications = applicationService.getApplicationsByUser(userId);
        if (applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/college/{collegeId}")
    public ResponseEntity<List<Application>> getApplicationsByCollege(@PathVariable int collegeId) {
        List<Application> applications = applicationService.getApplicationsByCollege(collegeId);
        if (applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Application>> getApplicationsByCourse(@PathVariable int courseId) {
        List<Application> applications = applicationService.getApplicationsByCourse(courseId);
        if (applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/accepted/{userId}")
    public List<ApplicationDTO> getAcceptedApplicationsByStudent(@PathVariable Integer userId) {
        return applicationService.getAcceptedApplicationsByStudent(userId);
    }


    @PutMapping("/{applicationId}/applicationStatus")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Integer applicationId,
            @RequestBody UpdateApplicationStatusDto appStatus) {
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(
                    applicationId, appStatus.getApplicationStatus());

            if (updatedApplication != null) {
                return ResponseEntity.ok(updatedApplication);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); 
        }
    }
    
    @GetMapping("/pending")
    public ResponseEntity<List<ApplicationResponseDto>> getPendingApplications() {
        List<ApplicationResponseDto> pendingApplications = applicationService.getPendingApplications();
        return ResponseEntity.ok(pendingApplications);
    }
    
    
}
