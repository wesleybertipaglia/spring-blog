package com.wesleybertipaglia.blog.mapper;

import com.wesleybertipaglia.blog.dtos.UserResponse;
import com.wesleybertipaglia.blog.model.User;

public class UserMapper {
    public static UserResponse convertToDTO(User user) {
        if (user == null) {
            return null;
        } else if (user.getId() == null) {
            throw new IllegalArgumentException("User ID is required");
        } else if (user.getUsername() == null) {
            throw new IllegalArgumentException("User username is required");
        } else if (user.getRole() == null) {
            throw new IllegalArgumentException("User role is required");
        }

        return new UserResponse(
                user.getId(),
                user.getUsername());
    }

    public static User convertToEntity(UserResponse userResponse) {
        if (userResponse == null) {
            return null;
        } else if (userResponse.id() == null) {
            throw new IllegalArgumentException("User ID is required");
        } else if (userResponse.username() == null) {
            throw new IllegalArgumentException("User username is required");
        }

        return new User(
                userResponse.id(),
                userResponse.username());
    }
}
