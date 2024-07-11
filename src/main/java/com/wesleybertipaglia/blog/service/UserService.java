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
import com.wesleybertipaglia.blog.dtos.user.UserRequestDTO;
import com.wesleybertipaglia.blog.dtos.user.UserResponseDTO;
import com.wesleybertipaglia.blog.mapper.UserMapper;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.repository.UserRepository;
import com.wesleybertipaglia.blog.repository.LikeRepository;
import com.wesleybertipaglia.blog.mapper.LikeMapper;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> listUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(UserMapper::convertToDTO);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        return UserMapper.convertToDTO(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getCurrentUser(String tokenSubject) {
        return UserMapper.convertToDTO(userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Transactional
    public Optional<UserResponseDTO> updateCurrentUser(UserRequestDTO userRequestDTO, String tokenSubject) {
        User user = userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUsername(userRequestDTO.username());
        user.setPassword(userRequestDTO.password());
        userRepository.save(user);

        return Optional.of(UserMapper.convertToDTO(user));
    }

    @Transactional
    public void deleteCurrentUser(String tokenSubject) {
        User user = userRepository.findById(UUID.fromString(tokenSubject))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public Page<LikeResponseDTO> listLikesOfCurrentUser(int page, int size, String tokenSubject) {
        Pageable pageable = PageRequest.of(page, size);
        return likeRepository.findByUserId(UUID.fromString(tokenSubject), pageable).map(LikeMapper::convertToDTO);
    }
}
