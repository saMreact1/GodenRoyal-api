package com.samreact.GoldenRoyalEmail.dto.response;

import com.samreact.GoldenRoyalEmail.data.enums.ResponseStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record ErrorResponse(@NotNull ResponseStatus status, @NotNull String message) {
    public ErrorResponse {
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(message, "message must not be null");
    }
}

