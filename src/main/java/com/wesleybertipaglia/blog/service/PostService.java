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
import com.wesleybertipaglia.blog.repository.PostRepository;
import com.wesleybertipaglia.blog.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<PostResponseDTO> createPost(PostCreateDTO postCreateDTO, String tokenSubject) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(tokenSubject));
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        Post post = PostMapper.convertToEntity(postCreateDTO, userOptional.get());
        postRepository.save(post);

        return Optional.of(PostMapper.convertToDTO(post));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDTO> listPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).map(PostMapper::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PostResponseDTO> getPost(UUID id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        return Optional.of(PostMapper.convertToDTO(postOptional.get()));
    }

    @Transactional
    public Optional<PostResponseDTO> updatePost(UUID id, PostCreateDTO postCreateDTO, String tokenSubject) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        Post post = postOptional.get();
        if (!post.getCreator().getId().equals(UUID.fromString(tokenSubject))) {
            throw new IllegalArgumentException("User is not the creator of the post");
        }

        post.setTitle(postCreateDTO.title());
        post.setContent(postCreateDTO.content());
        postRepository.save(post);

        return Optional.of(PostMapper.convertToDTO(post));
    }

    @Transactional
    public void deletePost(UUID id, String tokenSubject) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        Post post = postOptional.get();
        if (!post.getCreator().getId().equals(UUID.fromString(tokenSubject))) {
            throw new IllegalArgumentException("User is not the creator of the post");
        }

        postRepository.delete(post);
    }
}
