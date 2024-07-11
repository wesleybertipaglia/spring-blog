package com.wesleybertipaglia.blog.mapper;

import com.wesleybertipaglia.blog.dtos.comment.CommentResponseDTO;
import com.wesleybertipaglia.blog.model.Comment;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.model.Post;

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

    public static Comment convertToEntity(User user, Post post, String content) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        } else if (post == null) {
            throw new IllegalArgumentException("Post is required");
        } else if (content == null) {
            throw new IllegalArgumentException("Content is required");
        }

        return new Comment(user, post, content);
    }
}
