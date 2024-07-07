package com.wesleybertipaglia.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wesleybertipaglia.blog.dtos.post.PostCreateDTO;
import com.wesleybertipaglia.blog.dtos.post.PostResponseDTO;
import com.wesleybertipaglia.blog.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateDTO post, JwtAuthenticationToken token) {
        return ResponseEntity.of(postService.createPost(post, token.getName()));
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<PostResponseDTO>> listPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.listPosts(page, size));
    }
}
