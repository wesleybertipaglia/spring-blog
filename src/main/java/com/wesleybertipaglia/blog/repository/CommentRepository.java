package com.wesleybertipaglia.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesleybertipaglia.blog.model.Comment;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    int countByPostId(UUID postId);

    Page<Comment> findByPostId(UUID postId, Pageable pageable);

    Page<Comment> findByUserId(UUID userId, Pageable pageable);

    Optional<Comment> findByUserIdAndPostId(UUID userId, UUID postId);

    boolean existsByUserIdAndPostId(UUID userId, UUID postId);
}
