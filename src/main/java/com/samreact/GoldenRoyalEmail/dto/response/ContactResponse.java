package com.samreact.GoldenRoyalEmail.dto.response;

import com.samreact.GoldenRoyalEmail.data.enums.ResponseStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record ContactResponse(@NotNull ResponseStatus status, @NotNull String message) {
    public ContactResponse {
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(message, "message must not be null");
    }
}

