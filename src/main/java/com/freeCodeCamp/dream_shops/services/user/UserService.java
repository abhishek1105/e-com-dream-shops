package com.freeCodeCamp.dream_shops.services.user;

import com.freeCodeCamp.dream_shops.dto.UserDto;
import com.freeCodeCamp.dream_shops.model.User;
import com.freeCodeCamp.dream_shops.request.CreateUserRequest;
import com.freeCodeCamp.dream_shops.request.UpdateUserRequest;

public interface UserService {
    User getUserById(Long userId);

    User createUser(CreateUserRequest createRequest);

    User updateUser(UpdateUserRequest updateRequest, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
