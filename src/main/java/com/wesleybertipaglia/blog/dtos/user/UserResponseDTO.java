package com.wesleybertipaglia.blog.dtos.user;

import java.util.UUID;

public record UserResponseDTO(UUID id, String username) {
}