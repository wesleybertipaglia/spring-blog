package com.wesleybertipaglia.blog.controller;

import org.springframework.web.bind.annotation.RestController;

import com.wesleybertipaglia.blog.dtos.auth.SignInRequestDTO;
import com.wesleybertipaglia.blog.dtos.auth.SignInResponseDTO;
import com.wesleybertipaglia.blog.dtos.auth.SignUpRequest;
import com.wesleybertipaglia.blog.dtos.user.UserResponse;
import com.wesleybertipaglia.blog.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signin(@RequestBody SignInRequestDTO signInRequest) {
        return ResponseEntity.of(authService.signin(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.of(authService.signup(signUpRequest));
    }
}
