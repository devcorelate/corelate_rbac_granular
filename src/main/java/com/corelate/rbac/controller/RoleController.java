package com.corelate.rbac.controller;

import com.corelate.rbac.dto.ApiResponse;
import com.corelate.rbac.dto.AssignPermissionRequest;
import com.corelate.rbac.dto.CreateRoleRequest;
import com.corelate.rbac.entity.Role;
import com.corelate.rbac.service.RoleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ApiResponse<List<Role>> list() {
        return ApiResponse.success("Roles fetched", roleService.list());
    }

    @PostMapping
    public ApiResponse<Role> create(@Valid @RequestBody CreateRoleRequest request) {
        return ApiResponse.success("Role created", roleService.create(request));
    }

    @PostMapping("/{id}/permissions")
    public ApiResponse<Role> assignPermission(@PathVariable Long id, @RequestBody AssignPermissionRequest request) {
        return ApiResponse.success("Permission assigned", roleService.assignPermission(id, request.permissionId()));
    }
}
