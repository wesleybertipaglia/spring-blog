package com.wesleybertipaglia.blog.mapper;

import com.wesleybertipaglia.blog.dtos.like.LikeResponseDTO;
import com.wesleybertipaglia.blog.model.Like;

public class LikeMapper {
    public static LikeResponseDTO convertToDTO(Like like) {
        if (like == null) {
            return null;
        } else if (like.getId() == null) {
            throw new IllegalArgumentException("Like ID is required");
        } else if (like.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        } else if (like.getPost() == null) {
            throw new IllegalArgumentException("Post is required");
        }

        return new LikeResponseDTO(like.getId(), like.getUser().getId(), like.getPost().getId());
    }
}
