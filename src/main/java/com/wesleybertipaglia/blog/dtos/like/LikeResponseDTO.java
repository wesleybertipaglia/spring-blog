package com.wesleybertipaglia.blog.dtos.like;

import java.util.UUID;

public record LikeResponseDTO(UUID id, UUID userId, UUID postId) {
}
