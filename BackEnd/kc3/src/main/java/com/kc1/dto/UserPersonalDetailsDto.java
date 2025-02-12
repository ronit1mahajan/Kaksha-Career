package com.kc1.dto;

import java.time.LocalDate;

import com.kc1.entities.User.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPersonalDetailsDto {
    private int userId;
    private String name;
    private String email;
    private Role role;
    private LocalDate dateOfRegistration;
    private String jwt;

    public UserPersonalDetailsDto() {
    }


    @Override
    public String toString() {
        return "UserPersonalDetailsDto{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                '}';
    }


	public UserPersonalDetailsDto(int userId, String name, String email, Role role, LocalDate dateOfRegistration) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.role = role;
		this.dateOfRegistration = dateOfRegistration;
	}

}

