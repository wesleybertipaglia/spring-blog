package com.wesleybertipaglia.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wesleybertipaglia.blog.dtos.comment.CommentCreateDTO;
import com.wesleybertipaglia.blog.dtos.comment.CommentResponseDTO;
import com.wesleybertipaglia.blog.service.CommentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentCreateDTO comment,
            JwtAuthenticationToken token) {
        return ResponseEntity.of(commentService.createComment(token.getName(), comment));
    }

    @GetMapping("/post/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<CommentResponseDTO>> listComments(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.listCommentsByPost(page, size, id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable UUID id) {
        return ResponseEntity.of(commentService.getComment(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable UUID id,
            @RequestBody CommentCreateDTO comment, JwtAuthenticationToken token) {
        return ResponseEntity.of(commentService.updateComment(id, comment, token.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id, JwtAuthenticationToken token) {
        commentService.deleteComment(id, token.getName());
        return ResponseEntity.noContent().build();
    }
}
