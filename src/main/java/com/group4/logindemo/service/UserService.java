package com.group4.logindemo.service;

import com.group4.logindemo.model.User;
import com.group4.logindemo.repository.UserRepository;
import com.group4.logindemo.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// 其他导入...

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        try {
            logger.info("Creating user: {}", user.getUsername());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
        }
    }
}
