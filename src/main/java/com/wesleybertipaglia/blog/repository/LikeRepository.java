package com.wesleybertipaglia.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesleybertipaglia.blog.model.Like;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    int countByPostId(UUID postId);

    Page<Like> findByPostId(UUID postId, Pageable pageable);

    Page<Like> findByUserId(UUID userId, Pageable pageable);

    Optional<Like> findByUserIdAndPostId(UUID userId, UUID postId);

    boolean existsByUserIdAndPostId(UUID userId, UUID postId);
}
