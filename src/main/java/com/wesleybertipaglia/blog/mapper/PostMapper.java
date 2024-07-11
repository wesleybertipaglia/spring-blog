package com.wesleybertipaglia.blog.mapper;

import com.wesleybertipaglia.blog.dtos.post.PostCreateDTO;
import com.wesleybertipaglia.blog.dtos.post.PostResponseDTO;
import com.wesleybertipaglia.blog.model.Post;
import com.wesleybertipaglia.blog.model.User;

public class PostMapper {
    public static PostResponseDTO convertToDTO(Post post, int likesCount) {
        if (post == null) {
            return null;
        } else if (post.getId() == null) {
            throw new IllegalArgumentException("Post ID is required");
        } else if (post.getCreator() == null) {
            throw new IllegalArgumentException("Post creator is required");
        } else if (post.getTitle() == null) {
            throw new IllegalArgumentException("Post title is required");
        } else if (post.getContent() == null) {
            throw new IllegalArgumentException("Post content is required");
        }

        return new PostResponseDTO(post.getId(), post.getCreator().getUsername(), post.getTitle(), post.getContent(),
                likesCount);
    }

    public static Post convertToEntity(PostCreateDTO postCreateDTO, User user) {
        if (postCreateDTO == null) {
            throw new IllegalArgumentException("Post is required");
        } else if (user == null) {
            throw new IllegalArgumentException("User is required");
        } else if (postCreateDTO.title() == null) {
            throw new IllegalArgumentException("Post title is required");
        } else if (postCreateDTO.content() == null) {
            throw new IllegalArgumentException("Post content is required");
        }

        return new Post(user, postCreateDTO.title(), postCreateDTO.content());
    }
}
