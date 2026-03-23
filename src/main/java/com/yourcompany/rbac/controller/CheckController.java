package com.yourcompany.rbac.controller;

import com.yourcompany.rbac.dto.ApiResponse;
import com.yourcompany.rbac.dto.PermissionCheckRequest;
import com.yourcompany.rbac.dto.PermissionCheckResponse;
import com.yourcompany.rbac.service.RbacService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckController {

    private final RbacService rbacService;

    @PostMapping
    public ApiResponse<PermissionCheckResponse> check(@Valid @RequestBody PermissionCheckRequest request) {
        boolean allowed = rbacService.hasPermission(request.username(), request.permissionCode());
        return ApiResponse.success("Permission check completed", new PermissionCheckResponse(allowed));
    }
}
