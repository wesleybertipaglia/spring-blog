package com.wesleybertipaglia.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wesleybertipaglia.blog.dtos.comment.CommentResponseDTO;
import com.wesleybertipaglia.blog.dtos.like.LikeResponseDTO;
import com.wesleybertipaglia.blog.dtos.user.UserRequestDTO;
import com.wesleybertipaglia.blog.dtos.user.UserResponseDTO;
import com.wesleybertipaglia.blog.service.CommentService;
import com.wesleybertipaglia.blog.service.LikeService;
import com.wesleybertipaglia.blog.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/me")
public class MeController {
    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getCurrentUser(JwtAuthenticationToken token) {
        return ResponseEntity.ok(userService.getCurrentUser(token.getName()));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateCurrentUser(@RequestBody UserRequestDTO userRequestDTO,
            JwtAuthenticationToken token) {
        return ResponseEntity.of(userService.updateCurrentUser(userRequestDTO, token.getName()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(JwtAuthenticationToken token) {
        userService.deleteCurrentUser(token.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/likes")
    public ResponseEntity<Page<LikeResponseDTO>> listlikesOfCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            JwtAuthenticationToken token) {
        return ResponseEntity.ok(likeService.listLikesOfCurrentUser(page, size, token.getName()));
    }

    @GetMapping("/comments")
    public ResponseEntity<Page<CommentResponseDTO>> listCommentsOfCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            JwtAuthenticationToken token) {
        return ResponseEntity.ok(commentService.listCommentsOfCurrentUser(page, size, token.getName()));
    }
}
