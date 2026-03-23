package com.corelate.rbac.controller;

import com.corelate.rbac.dto.ApiResponse;
import com.corelate.rbac.dto.AssignRoleRequest;
import com.corelate.rbac.dto.CreateUserRequest;
import com.corelate.rbac.entity.User;
import com.corelate.rbac.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<List<User>> list() {
        return ApiResponse.success("Users fetched", userService.list());
    }

    @PostMapping
    public ApiResponse<User> create(@Valid @RequestBody CreateUserRequest request) {
        return ApiResponse.success("User created", userService.create(request));
    }

    @PostMapping("/{id}/roles")
    public ApiResponse<User> assignRole(@PathVariable Long id, @RequestBody AssignRoleRequest request) {
        return ApiResponse.success("Role assigned", userService.assignRole(id, request.roleId()));
    }
}
