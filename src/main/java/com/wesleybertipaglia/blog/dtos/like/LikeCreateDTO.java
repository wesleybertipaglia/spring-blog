package com.wesleybertipaglia.blog.dtos.like;

import java.util.UUID;

public record LikeCreateDTO(UUID id, UUID userId, UUID postId) {
}
