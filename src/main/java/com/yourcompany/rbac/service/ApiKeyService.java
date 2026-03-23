package com.yourcompany.rbac.service;

import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.exception.UnauthorizedException;
import com.yourcompany.rbac.repository.ClientAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ClientAppRepository clientAppRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientApp validateApiKey(String apiKey) {
        return clientAppRepository.findAll().stream()
                .filter(ClientApp::isActive)
                .filter(client -> passwordEncoder.matches(apiKey, client.getApiKeyHash()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Invalid API key"));
    }
}
