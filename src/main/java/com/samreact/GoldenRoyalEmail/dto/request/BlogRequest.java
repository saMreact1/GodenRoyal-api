package com.samreact.GoldenRoyalEmail.dto.request;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlogRequest(
    @NotBlank @Size(max = 200) String title,
    @NotBlank @Size(max = 250) String slug,
    @NotBlank @Size(max = 500) String summary,
    @NotBlank String content,
    @NotBlank @Size(max = 100) String author,
    @Size(max = 500) String featuredImage,
    @NotNull BlogCategory category,
    BlogStatus status
) {}

