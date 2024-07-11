package com.wesleybertipaglia.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.repository.LikeRepository;
import com.wesleybertipaglia.blog.repository.PostRepository;
import com.wesleybertipaglia.blog.repository.UserRepository;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

}
