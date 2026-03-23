package com.yourcompany.rbac.controller;

import com.yourcompany.rbac.dto.ApiResponse;
import com.yourcompany.rbac.dto.LoginRequest;
import com.yourcompany.rbac.dto.RefreshTokenRequest;
import com.yourcompany.rbac.dto.TokenResponse;
import com.yourcompany.rbac.service.AuthService;
import com.yourcompany.rbac.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success("Login successful", authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<String> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        refreshTokenService.validateToken(request.refreshToken());
        return ApiResponse.success("Refresh token is valid", "valid");
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        return ApiResponse.success("Logout successful", "ok");
    }
}
