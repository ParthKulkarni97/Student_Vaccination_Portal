package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.UserRegistrationRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.UserUpdateRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.ApiResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.UserResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.User;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// controller/UserController.java
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        log.info("Creating new user: {}", request.getUsername());
        User user = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(mapToUserResponse(user)));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request) {
        log.info("Updating user: {}", userId);
        User user = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success(mapToUserResponse(user)));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable String userId) {
        log.info("Deactivating user: {}", userId);
        userService.deactivateUser(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("Fetching all users");
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .isActive(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
