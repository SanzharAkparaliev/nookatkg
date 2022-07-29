package com.example.nookatkg.service;

import com.example.nookatkg.model.User;

public interface UserService {
     void createUser(String user,String email,String password);
     User getUser(String userName);

}
