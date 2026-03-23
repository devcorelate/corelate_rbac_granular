package com.corelate.rbac.controller;

import com.corelate.rbac.dto.ApiResponse;
import com.corelate.rbac.dto.PermissionCheckRequest;
import com.corelate.rbac.dto.PermissionCheckResponse;
import com.corelate.rbac.service.RbacService;
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
