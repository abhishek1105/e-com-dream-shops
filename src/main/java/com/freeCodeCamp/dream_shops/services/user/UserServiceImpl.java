package com.freeCodeCamp.dream_shops.services.user;

import com.freeCodeCamp.dream_shops.dto.UserDto;
import com.freeCodeCamp.dream_shops.exceptions.AlreadyExistsException;
import com.freeCodeCamp.dream_shops.exceptions.ResourceNotFoundException;
import com.freeCodeCamp.dream_shops.model.User;
import com.freeCodeCamp.dream_shops.repo.UserRepo;
import com.freeCodeCamp.dream_shops.request.CreateUserRequest;
import com.freeCodeCamp.dream_shops.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createUser(CreateUserRequest createRequest) {
        return Optional.of(createRequest).filter(user -> !userRepo.existsByEmail(createRequest.getEmail())).map(req -> {
            User user = new User();
            user.setFirstName(createRequest.getFirstName());
            user.setLastName(createRequest.getLastName());
            user.setEmail(createRequest.getEmail());
            user.setPassword(passwordEncoder.encode(createRequest.getPassword()));
            return userRepo.save(user);
        }).orElseThrow(() -> new AlreadyExistsException("oops this " + createRequest.getEmail() + " Already Exists"));
    }

    @Override
    public User updateUser(UpdateUserRequest updateRequest, Long userId) {
        return userRepo.findById(userId).map(existingUser -> {
            existingUser.setFirstName(updateRequest.getFirstName());
            existingUser.setLastName(updateRequest.getLastName());
            return userRepo.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found hence cant update"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete, () -> {
            throw new ResourceNotFoundException("User not found hence cant delete");
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByEmail(email);

    }
}
