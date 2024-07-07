package com.wesleybertipaglia.blog.dtos.auth;

public record SignInResponseDTO(String accessToken, Long expiresIn) {
}