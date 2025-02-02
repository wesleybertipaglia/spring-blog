package com.wesleybertipaglia.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wesleybertipaglia.blog.dtos.like.LikeResponseDTO;
import com.wesleybertipaglia.blog.service.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/post/{id}")
    public ResponseEntity<LikeResponseDTO> createLike(@PathVariable UUID id, JwtAuthenticationToken token) {
        return ResponseEntity.of(likeService.createLike(id, token.getName()));
    }

    @GetMapping("/post/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<LikeResponseDTO>> listComments(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(likeService.listLikes(page, size, id));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id, JwtAuthenticationToken token) {
        likeService.deleteLike(id, token.getName());
        return ResponseEntity.noContent().build();
    }
}
