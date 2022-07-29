package com.example.nookatkg.service.impl;

import com.example.nookatkg.model.User;
import com.example.nookatkg.repo.UserRepository;
import com.example.nookatkg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(String userName,String email,String password) {
        User user = new User();
        user.setRole("ROLE_USER");
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User getUser(String  user) {
        return userRepository.getUserByUsername(user);
    }
}
