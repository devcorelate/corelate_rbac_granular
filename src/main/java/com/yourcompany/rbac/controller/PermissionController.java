package com.yourcompany.rbac.controller;

import com.yourcompany.rbac.dto.ApiResponse;
import com.yourcompany.rbac.dto.CreatePermissionRequest;
import com.yourcompany.rbac.entity.Permission;
import com.yourcompany.rbac.service.PermissionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ApiResponse<List<Permission>> list() {
        return ApiResponse.success("Permissions fetched", permissionService.list());
    }

    @PostMapping
    public ApiResponse<Permission> create(@Valid @RequestBody CreatePermissionRequest request) {
        return ApiResponse.success("Permission created", permissionService.create(request));
    }
}
