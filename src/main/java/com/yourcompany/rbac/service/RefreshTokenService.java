package com.yourcompany.rbac.service;

import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.entity.RefreshToken;
import com.yourcompany.rbac.entity.User;
import com.yourcompany.rbac.exception.UnauthorizedException;
import com.yourcompany.rbac.repository.RefreshTokenRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String issueRefreshToken(User user, ClientApp clientApp) {
        String rawToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setClientApp(clientApp);
        refreshToken.setTokenHash(passwordEncoder.encode(rawToken));
        refreshToken.setExpiresAt(OffsetDateTime.now().plusSeconds(refreshTokenExpiration / 1000));
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    public void validateToken(String token) {
        boolean valid = refreshTokenRepository.findAll().stream()
                .filter(rt -> !rt.isRevoked())
                .anyMatch(rt -> passwordEncoder.matches(token, rt.getTokenHash())
                        && rt.getExpiresAt().isAfter(OffsetDateTime.now()));
        if (!valid) {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }
}
