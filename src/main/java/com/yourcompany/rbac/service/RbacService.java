package com.yourcompany.rbac.service;

import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.entity.User;
import com.yourcompany.rbac.repository.PermissionRepository;
import com.yourcompany.rbac.repository.UserRepository;
import com.yourcompany.rbac.util.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RbacService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public boolean hasPermission(String username, String permissionCode) {
        ClientApp clientApp = TenantContext.getTenant();
        User user = userRepository.findByUsernameAndClientApp(username, clientApp).orElse(null);
        if (user == null) {
            return false;
        }
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.getCode().equals(permissionCode)
                        && permissionRepository.findByCodeAndClientApp(permissionCode, clientApp).isPresent());
    }
}
