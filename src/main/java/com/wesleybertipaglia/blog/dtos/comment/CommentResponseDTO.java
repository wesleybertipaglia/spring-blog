package com.wesleybertipaglia.blog.dtos.comment;

import java.util.UUID;

public record CommentResponseDTO(UUID id, UUID userId, UUID postId) {
}
