package com.footonomy.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * docs/07_Data_Dictionary.md's User table only has email/password_hash/created_at as fields,
 * and there's no separate change-password endpoint in the frozen contract (docs/03_TRD.md §4),
 * so profile editing (FR-19) is scoped to email here. Flag to the human before adding a
 * password field to this endpoint.
 */
public record UpdateUserRequest(@NotBlank @Email String email) {
}
