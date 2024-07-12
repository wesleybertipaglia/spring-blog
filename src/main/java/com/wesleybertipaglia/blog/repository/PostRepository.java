package com.wesleybertipaglia.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wesleybertipaglia.blog.model.Post;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findAllByCreatorId(UUID creatorId, Pageable pageable);

    Optional<Post> findByIdAndCreatorId(UUID id, UUID creatorId);
}
