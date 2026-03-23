package com.corelate.rbac.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleRequest(@NotBlank String name, @NotBlank String code) {
}
