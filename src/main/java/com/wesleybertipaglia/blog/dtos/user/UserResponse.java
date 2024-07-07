package com.wesleybertipaglia.blog.dtos.user;

import java.util.UUID;

public record UserResponse(UUID id, String username) {
}