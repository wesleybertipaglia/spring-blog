package com.wesleybertipaglia.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.model.Post;
import com.wesleybertipaglia.blog.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<Post> listPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }
}
