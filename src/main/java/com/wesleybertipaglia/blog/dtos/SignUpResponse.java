package com.wesleybertipaglia.blog.dtos;

import java.util.UUID;

public record SignUpResponse(UUID id, String username, String role) {
}