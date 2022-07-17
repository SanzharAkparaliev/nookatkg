package com.example.nookatkg.service;

import com.example.nookatkg.model.User;

public interface UserService {
     void createUser(User user);
     User getUser(String user);

}
