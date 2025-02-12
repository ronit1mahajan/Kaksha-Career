package com.kc1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kc1.dto.LoginDTO;
import com.kc1.dto.UpdateUserDto;
import com.kc1.dto.UserPersonalDetailsDto;
import com.kc1.entities.User;
import com.kc1.exceptions.ErrorResponse;
import com.kc1.exceptions.ResourceNotFoundException;
import com.kc1.security.CustomUserDetails;
import com.kc1.security.JwtUtils;
import com.kc1.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    
    private AuthenticationManager authMgr;

    private JwtUtils jwtUtils;

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpSession session) {
    			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
    					loginDTO.getPassword());
    			Authentication verifiedToken = authMgr.authenticate(token);
    			String jwt = jwtUtils.generateJwtToken(verifiedToken);
    			CustomUserDetails userDetails = (CustomUserDetails) verifiedToken.getPrincipal();
    			User user = userDetails.getUser();
    			
    			UserPersonalDetailsDto responseDTO=new UserPersonalDetailsDto(
    	                user.getUserId(),
    	                user.getName(),
    	                user.getEmail(),
    	                user.getRole(),
    	                user.getDateOfRegistration(),  
    	                jwt
    	        );     

    			return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UpdateUserDto registerDTO, HttpSession session) {
        try {
            User user = userService.registerUser(registerDTO);
            return new ResponseEntity<>("Login successful with user Id: "+user.getUserId(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse("User not found or invalid password", 401), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Integer userId,
            @RequestBody UpdateUserDto userDTO) {

        User updatedUser = userService.updateUser(userId, userDTO);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
    
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(HttpSession session, @PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            session.invalidate(); 
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse("User not found", 404), HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId){
    	try {
    		User user=userService.getUserByUserID(userId);
    		return new ResponseEntity<>(user, HttpStatus.OK);
    	}catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse("User not found", 404), HttpStatus.NOT_FOUND);
        }
    }
}
