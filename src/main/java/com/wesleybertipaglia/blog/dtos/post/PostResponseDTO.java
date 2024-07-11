package com.wesleybertipaglia.blog.dtos.post;

import java.util.UUID;

public record PostResponseDTO(UUID id, String creator, String title, String content, int likesCount) {
}
