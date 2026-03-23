package com.corelate.rbac.service;

import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.exception.UnauthorizedException;
import com.corelate.rbac.repository.ClientAppRepository;
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
