package com.kc1.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kc1.dto.CollegeSearchDto;
import com.kc1.dto.LoginDTO;
import com.kc1.dto.UpdateUserDto;
import com.kc1.dto.UserPersonalDetailsDto;
import com.kc1.entities.College;
import com.kc1.entities.User;
import com.kc1.entities.User.Role;
import com.kc1.exceptions.ResourceNotFoundException;
import com.kc1.repositories.CollegeDao;
import com.kc1.repositories.UserDao;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao uDao;
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private CollegeDao collegeDao;

    @Override
    public User login(LoginDTO loginDTO) {
        User user = null;
		try {
			user = uDao.findByEmail(loginDTO.getEmail())
			        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user; 
    }
    
    @Override
    public User registerUser(UpdateUserDto userDto) {
        if (uDao.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user=mapper.map(userDto, User.class);
        if (user.getRole() == null || user.getRole().toString()=="") {
            user.setRole(Role.STUDENT);; 
        }
        
        User saveduser = uDao.save(user);
        if(saveduser != null) {
        	emailService.sendEmail(user.getEmail(), "Welcome to Kaksha Career", 
                    "Dear " + user.getName() + ",\n\nWelcome to our Kaksha Career.\n\nBest regards,\nAdmin Team");
        }
        return saveduser;
    }

    @Override
    public User registerUser(User user) {
        if (uDao.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return uDao.save(user);
    }

    @Override
    public UserPersonalDetailsDto getUserDetails(Integer userId) {
        User user = null;
		try {
			user = uDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
        return new UserPersonalDetailsDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getDateOfRegistration()
        );
    }

    @Override
    public User updateUser(Integer userId, UpdateUserDto dto) {
        User user = uDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (uDao.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(dto.getEmail());
        }

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return uDao.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = null;
		try {
			user = uDao.findById(userId)
			        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

        uDao.delete(user);
    }

    @Override
    public List<College> searchColleges(CollegeSearchDto searchDto) {
        List<College> colleges;

        if (searchDto.getName() != null && !searchDto.getName().isEmpty()) {
            colleges = collegeDao.findByNameContainingIgnoreCase(searchDto.getName());
        } else if (searchDto.getLocation() != null && !searchDto.getLocation().isEmpty()) {
            colleges = collegeDao.findByLocationContainingIgnoreCase(searchDto.getLocation());
        } else {
            colleges = collegeDao.findAll();
        }

        return colleges;
    }
    @Override
    public College viewCollegeDetails(Integer collegeId) throws ResourceNotFoundException {
        return collegeDao.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("College not found"));
    }

	@Override
	public User getUserByUserID(Integer id) {
		
		return uDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("College not found"));
	}

}
