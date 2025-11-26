package com.samreact.GoldenRoyalEmail.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @NotBlank @Size(max = 50)
        String name,

        @NotBlank @Email @Size(max = 100)
        String email,

        @NotBlank @Size(max = 2000)
        String message
) {
}
