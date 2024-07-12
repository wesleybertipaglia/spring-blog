package com.wesleybertipaglia.blog.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.dtos.comment.CommentCreateDTO;
import com.wesleybertipaglia.blog.dtos.comment.CommentResponseDTO;
import com.wesleybertipaglia.blog.mapper.CommentMapper;
import com.wesleybertipaglia.blog.model.Comment;
import com.wesleybertipaglia.blog.model.Post;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.repository.CommentRepository;
import com.wesleybertipaglia.blog.repository.PostRepository;
import com.wesleybertipaglia.blog.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<CommentResponseDTO> createComment(String tokenSubject, CommentCreateDTO commentCreateDTO) {
        Post post = postRepository.findById(
                commentCreateDTO.postId()).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        User user = userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = CommentMapper.convertToEntity(user, post, commentCreateDTO.content());
        return Optional.of(CommentMapper.convertToDTO(commentRepository.save(comment)));
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDTO> listCommentsByPost(int page, int size, UUID postId) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByPostId(postId, pageable).map(CommentMapper::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDTO> listCommentsOfCurrentUser(int page, int size, String tokenSubject) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return commentRepository.findByUserId(user.getId(), pageable).map(CommentMapper::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Optional<CommentResponseDTO> getComment(UUID id) {
        return commentRepository.findById(id).map(CommentMapper::convertToDTO);
    }

    @Transactional
    public Optional<CommentResponseDTO> updateComment(UUID id, CommentCreateDTO commentCreateDTO, String tokenSubject) {
        Comment comment = commentRepository.findByIdAndUserId(id, UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.setContent(commentCreateDTO.content());
        return Optional.of(CommentMapper.convertToDTO(commentRepository.save(comment)));
    }

    @Transactional
    public void deleteComment(UUID id, String tokenSubject) {
        Comment comment = commentRepository.findByIdAndUserId(id, UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        commentRepository.delete(comment);
    }
}
