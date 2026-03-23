package com.yourcompany.rbac.service;

import com.yourcompany.rbac.dto.CreatePermissionRequest;
import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.entity.Permission;
import com.yourcompany.rbac.repository.PermissionRepository;
import com.yourcompany.rbac.util.TenantContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> list() {
        ClientApp clientApp = TenantContext.getTenant();
        return permissionRepository.findAllByClientApp(clientApp);
    }

    public Permission create(CreatePermissionRequest request) {
        ClientApp clientApp = TenantContext.getTenant();
        Permission permission = new Permission();
        permission.setClientApp(clientApp);
        permission.setName(request.name());
        permission.setCode(request.code());
        return permissionRepository.save(permission);
    }
}
