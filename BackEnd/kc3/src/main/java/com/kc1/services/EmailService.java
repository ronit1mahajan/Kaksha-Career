package com.kc1.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kc1.entities.User;
import com.kc1.entities.User.Role;
import com.kc1.repositories.UserDao;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDao userRepository;

    public void sendEmail(String to, String subject, String body) {
        Optional<User> adminOpt = userRepository.findFirstByRole(Role.ADMIN);
        if (adminOpt.isEmpty()) {
            throw new RuntimeException("No admin found to send email.");
        }

        String adminEmail = adminOpt.get().getEmail();
        System.out.println("generated values : " + adminEmail + to + subject);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(adminEmail); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
