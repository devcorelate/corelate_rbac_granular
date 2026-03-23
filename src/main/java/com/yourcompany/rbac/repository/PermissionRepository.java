package com.yourcompany.rbac.repository;

import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.entity.Permission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByClientApp(ClientApp clientApp);
    Optional<Permission> findByIdAndClientApp(Long id, ClientApp clientApp);
    Optional<Permission> findByCodeAndClientApp(String code, ClientApp clientApp);
}
