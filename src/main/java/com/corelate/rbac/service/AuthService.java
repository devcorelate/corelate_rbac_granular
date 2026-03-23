package com.corelate.rbac.service;

import com.corelate.rbac.dto.LoginRequest;
import com.corelate.rbac.dto.TokenResponse;
import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.User;
import com.corelate.rbac.exception.UnauthorizedException;
import com.corelate.rbac.repository.UserRepository;
import com.corelate.rbac.util.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public TokenResponse login(LoginRequest request) {
        ClientApp clientApp = TenantContext.getTenant();
        User user = userRepository.findByUsernameAndClientApp(request.username(), clientApp)
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = refreshTokenService.issueRefreshToken(user, clientApp);
        return new TokenResponse(accessToken, refreshToken);
    }
}
