package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User register(String username, String password);
    User login(String username, String password);
}