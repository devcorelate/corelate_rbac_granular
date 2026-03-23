package com.corelate.rbac.service;

import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.User;
import com.corelate.rbac.repository.PermissionRepository;
import com.corelate.rbac.repository.UserRepository;
import com.corelate.rbac.util.TenantContext;
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
