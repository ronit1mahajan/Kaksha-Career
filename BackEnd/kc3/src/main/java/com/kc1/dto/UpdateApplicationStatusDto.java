package com.kc1.dto;

import com.kc1.entities.Application.ApplicationStatus;

public class UpdateApplicationStatusDto {

    private ApplicationStatus applicationStatus;

    public UpdateApplicationStatusDto() {
		// TODO Auto-generated constructor stub
	}
    
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}

