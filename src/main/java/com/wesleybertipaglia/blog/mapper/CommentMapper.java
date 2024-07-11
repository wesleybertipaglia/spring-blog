package com.wesleybertipaglia.blog.mapper;

import com.wesleybertipaglia.blog.dtos.comment.CommentResponseDTO;
import com.wesleybertipaglia.blog.model.Comment;

public class CommentMapper {
    public static CommentResponseDTO convertToDTO(Comment comment) {
        if (comment == null) {
            return null;
        } else if (comment.getId() == null) {
            throw new IllegalArgumentException("ID is required");
        } else if (comment.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        } else if (comment.getPost() == null) {
            throw new IllegalArgumentException("Post is required");
        } else if (comment.getContent() == null) {
            throw new IllegalArgumentException("Content is required");
        }

        return new CommentResponseDTO(comment.getId(), comment.getUser().getId(), comment.getPost().getId(),
                comment.getContent());
    }
}
