package com.corelate.rbac.service;

import com.corelate.rbac.dto.CreatePermissionRequest;
import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.Permission;
import com.corelate.rbac.repository.PermissionRepository;
import com.corelate.rbac.util.TenantContext;
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
