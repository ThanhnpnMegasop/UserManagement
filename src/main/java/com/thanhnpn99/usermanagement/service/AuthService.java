package com.thanhnpn99.usermanagement.service;

import com.thanhnpn99.usermanagement.dto.LoginDto;
import com.thanhnpn99.usermanagement.dto.SignUpDto;
import com.thanhnpn99.usermanagement.entity.User;

import java.util.List;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(SignUpDto signUpDto);

    List<User> getAllUsers();
}
