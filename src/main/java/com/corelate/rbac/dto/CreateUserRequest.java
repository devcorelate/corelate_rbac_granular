package com.corelate.rbac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank String username, @Email String email, @NotBlank String password) {
}
