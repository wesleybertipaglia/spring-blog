package com.wesleybertipaglia.blog.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.dtos.auth.SignInRequestDTO;
import com.wesleybertipaglia.blog.dtos.auth.SignInResponseDTO;
import com.wesleybertipaglia.blog.dtos.auth.SignUpRequestDTO;
import com.wesleybertipaglia.blog.dtos.user.UserResponse;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Optional<SignInResponseDTO> signin(SignInRequestDTO signInRequest) {
        User user = userRepository.findByUsername(signInRequest.username());

        if (user == null || !passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Instant now = Instant.now();
        Long expirationTime = 300L;

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("blog")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .claim("role", user.getRole())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return Optional.of(new SignInResponseDTO(token, expirationTime));
    }

    @Transactional
    public Optional<UserResponse> signup(SignUpRequestDTO signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.username()) != null) {
            throw new BadCredentialsException("Username already exists");
        }

        User user = new User(signUpRequest.username(), passwordEncoder.encode(signUpRequest.password()),
                signUpRequest.role());
        userRepository.save(user);
        return Optional.of(new UserResponse(user.getId(), signUpRequest.username()));
    }
}