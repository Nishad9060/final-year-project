package com.footonomy.following.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record CreateFollowingRequest(
        @NotNull @Pattern(regexp = "team|tournament", message = "entityType must be 'team' or 'tournament'")
        String entityType,
        @NotNull UUID entityId) {
}
