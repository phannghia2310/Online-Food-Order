package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUserCustomer();
    User createUser(User user);
    User updateUser(User user);
    User findUserById(String email);
    boolean isUserEmailExist(String email);
}
