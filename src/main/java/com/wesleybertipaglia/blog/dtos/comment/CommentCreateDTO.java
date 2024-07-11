package com.wesleybertipaglia.blog.dtos.comment;

import java.util.UUID;

public record CommentCreateDTO(UUID postId, String content) {
}
