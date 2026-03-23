package com.corelate.rbac.dto;

import jakarta.validation.constraints.NotBlank;

public record PermissionCheckRequest(@NotBlank String username, @NotBlank String permissionCode) {
}
