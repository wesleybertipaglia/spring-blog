package com.wesleybertipaglia.blog.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wesleybertipaglia.blog.dtos.user.UserRequestDTO;
import com.wesleybertipaglia.blog.dtos.user.UserResponseDTO;
import com.wesleybertipaglia.blog.mapper.UserMapper;
import com.wesleybertipaglia.blog.model.User;
import com.wesleybertipaglia.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
}
