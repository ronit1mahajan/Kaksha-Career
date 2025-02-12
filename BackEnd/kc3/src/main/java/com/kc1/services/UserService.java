package com.kc1.services;

import java.util.List;

import com.kc1.dto.CollegeSearchDto;
import com.kc1.dto.LoginDTO;
import com.kc1.dto.UpdateUserDto;
import com.kc1.dto.UserPersonalDetailsDto;
import com.kc1.entities.College;
import com.kc1.entities.User;
import com.kc1.exceptions.ResourceNotFoundException;

public interface UserService {

    User login(LoginDTO loginDTO) throws ResourceNotFoundException;

    User registerUser(User user);

    UserPersonalDetailsDto getUserDetails(Integer userId) throws ResourceNotFoundException;

    User updateUser(Integer userId, UpdateUserDto dto) throws ResourceNotFoundException;

    void deleteUser(Integer userId) throws ResourceNotFoundException;

    List<College> searchColleges(CollegeSearchDto searchDto);

    College viewCollegeDetails(Integer collegeId) throws ResourceNotFoundException;

	User registerUser(UpdateUserDto registerDTO);

	User getUserByUserID(Integer id);
}
