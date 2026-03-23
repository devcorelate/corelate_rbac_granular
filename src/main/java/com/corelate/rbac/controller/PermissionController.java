package com.corelate.rbac.controller;

import com.corelate.rbac.dto.ApiResponse;
import com.corelate.rbac.dto.CreatePermissionRequest;
import com.corelate.rbac.entity.Permission;
import com.corelate.rbac.service.PermissionService;
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
