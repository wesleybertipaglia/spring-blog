package com.wesleybertipaglia.blog.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.dtos.like.LikeResponseDTO;
import com.wesleybertipaglia.blog.mapper.LikeMapper;
import com.wesleybertipaglia.blog.model.Like;
import com.wesleybertipaglia.blog.model.Post;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.repository.LikeRepository;
import com.wesleybertipaglia.blog.repository.PostRepository;
import com.wesleybertipaglia.blog.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<LikeResponseDTO> createLike(UUID postId, String tokenSubject) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        User user = userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (likeRepository.existsByUserIdAndPostId(user.getId(), post.getId())) {
            throw new EntityExistsException("Like already exists");
        }

        Like like = new Like(user, post);
        return Optional.of(LikeMapper.convertToDTO(likeRepository.save(like)));
    }

    @Transactional(readOnly = true)
    public Page<LikeResponseDTO> listLikes(int page, int size, UUID postId) {
        Pageable pageable = PageRequest.of(page, size);
        return likeRepository.findByPostId(postId, pageable).map(LikeMapper::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<LikeResponseDTO> listLikesOfCurrentUser(int page, int size, String tokenSubject) {
        Pageable pageable = PageRequest.of(page, size);
        return likeRepository.findByUserId(UUID.fromString(tokenSubject), pageable).map(LikeMapper::convertToDTO);
    }

    @Transactional
    public void deleteLike(UUID postId, String tokenSubject) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Like like = likeRepository.findByUserIdAndPostId(UUID.fromString(tokenSubject), post.getId())
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));

        likeRepository.delete(like);
    }
}
