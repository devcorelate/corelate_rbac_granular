package com.corelate.rbac.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePermissionRequest(@NotBlank String name, @NotBlank String code) {
}
