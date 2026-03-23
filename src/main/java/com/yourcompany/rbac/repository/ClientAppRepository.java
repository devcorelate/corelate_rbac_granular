package com.yourcompany.rbac.repository;

import com.yourcompany.rbac.entity.ClientApp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAppRepository extends JpaRepository<ClientApp, Long> {
    Optional<ClientApp> findByApiKeyHashAndActiveTrue(String apiKeyHash);
}
