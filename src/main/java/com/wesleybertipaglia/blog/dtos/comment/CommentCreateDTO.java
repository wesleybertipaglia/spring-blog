package com.wesleybertipaglia.blog.dtos.comment;

import java.util.UUID;

public record CommentCreateDTO(UUID id, UUID userId, UUID postId) {
}
