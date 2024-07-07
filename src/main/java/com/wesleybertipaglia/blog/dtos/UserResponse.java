package com.wesleybertipaglia.blog.dtos;

import java.util.UUID;

public record UserResponse(UUID id, String username) {
}