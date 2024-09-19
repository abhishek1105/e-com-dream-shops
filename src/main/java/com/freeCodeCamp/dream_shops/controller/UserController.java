package com.freeCodeCamp.dream_shops.controller;

import com.freeCodeCamp.dream_shops.exceptions.ResourceNotFoundException;
import com.freeCodeCamp.dream_shops.model.User;
import com.freeCodeCamp.dream_shops.response.ApiResponse;
import com.freeCodeCamp.dream_shops.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("User found", user));
        } catch (ResourceNotFoundException e) {
        }
    }


}
