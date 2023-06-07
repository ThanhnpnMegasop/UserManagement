package com.thanhnpn99.usermanagement.service;

import com.thanhnpn99.usermanagement.dto.LoginDto;
import com.thanhnpn99.usermanagement.dto.SignUpDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(SignUpDto signUpDto);
}
