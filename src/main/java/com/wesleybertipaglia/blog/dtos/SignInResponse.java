package com.wesleybertipaglia.blog.dtos;

public record SignInResponse(String accessToken, Long expiresIn) {
}