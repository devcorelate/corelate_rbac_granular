package com.corelate.rbac.service;

import com.corelate.rbac.dto.CreateRoleRequest;
import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.Permission;
import com.corelate.rbac.entity.Role;
import com.corelate.rbac.exception.NotFoundException;
import com.corelate.rbac.repository.PermissionRepository;
import com.corelate.rbac.repository.RoleRepository;
import com.corelate.rbac.util.TenantContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<Role> list() {
        ClientApp clientApp = TenantContext.getTenant();
        return roleRepository.findAllByClientApp(clientApp);
    }

    public Role create(CreateRoleRequest request) {
        ClientApp clientApp = TenantContext.getTenant();
        Role role = new Role();
        role.setClientApp(clientApp);
        role.setName(request.name());
        role.setCode(request.code());
        return roleRepository.save(role);
    }

    public Role assignPermission(Long roleId, Long permissionId) {
        ClientApp clientApp = TenantContext.getTenant();
        Role role = roleRepository.findByIdAndClientApp(roleId, clientApp)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        Permission permission = permissionRepository.findByIdAndClientApp(permissionId, clientApp)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        role.getPermissions().add(permission);
        return roleRepository.save(role);
    }
}
