package com.thanhnpn99.usermanagement.controller;

import com.thanhnpn99.usermanagement.dto.JWTAuthResponse;
import com.thanhnpn99.usermanagement.dto.LoginDto;
import com.thanhnpn99.usermanagement.dto.SignUpDto;
import com.thanhnpn99.usermanagement.entity.User;
import com.thanhnpn99.usermanagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){
        String response = authService.register(signUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Get All Users REST API
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @GetMapping(value = {"/users"})
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = authService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PutMapping(value = {"/activate_account"})
    public ResponseEntity<String> activateAccount(@RequestBody User users) {
        String response = authService.activateAccount(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}