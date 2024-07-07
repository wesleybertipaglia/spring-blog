package com.wesleybertipaglia.blog.dtos.auth;

public record SignInResponse(String accessToken, Long expiresIn) {
}