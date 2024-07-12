package com.wesleybertipaglia.blog.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.dtos.post.PostCreateDTO;
import com.wesleybertipaglia.blog.dtos.post.PostResponseDTO;
import com.wesleybertipaglia.blog.mapper.PostMapper;
import com.wesleybertipaglia.blog.model.Post;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.repository.LikeRepository;
import com.wesleybertipaglia.blog.repository.PostRepository;
import com.wesleybertipaglia.blog.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public Optional<PostResponseDTO> createPost(PostCreateDTO postCreateDTO, String tokenSubject) {
        User user = userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Post post = PostMapper.convertToEntity(postCreateDTO, user);
        postRepository.save(post);

        return Optional.of(PostMapper.convertToDTO(post, 0));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDTO> listPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).map(post -> {
            int likesCount = likeRepository.countByPostId(post.getId());
            return PostMapper.convertToDTO(post, likesCount);
        });
    }

    @Transactional(readOnly = true)
    public Optional<PostResponseDTO> getPost(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        int likesCount = likeRepository.countByPostId(id);
        return Optional.of(PostMapper.convertToDTO(post, likesCount));
    }

    @Transactional
    public Optional<PostResponseDTO> updatePost(UUID id, PostCreateDTO postCreateDTO, String tokenSubject) {
        Post post = postRepository.findByIdAndUserId(id, UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        post.setTitle(postCreateDTO.title());
        post.setContent(postCreateDTO.content());
        postRepository.save(post);

        int likesCount = likeRepository.countByPostId(post.getId());
        return Optional.of(PostMapper.convertToDTO(post, likesCount));
    }

    @Transactional
    public void deletePost(UUID id, String tokenSubject) {
        Post post = postRepository.findByIdAndUserId(id, UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        postRepository.delete(post);
    }
}
