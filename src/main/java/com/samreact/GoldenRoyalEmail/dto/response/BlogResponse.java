package com.samreact.GoldenRoyalEmail.dto.response;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import com.samreact.GoldenRoyalEmail.data.model.Blog;

import java.time.LocalDateTime;

public record BlogResponse(
    Long id,
    String title,
    String slug,
    String summary,
    String content,
    String author,
    String featuredImage,
    BlogCategory category,
    BlogStatus status,
    LocalDateTime publishedAt,
    Long viewCount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static BlogResponse from(Blog blog) {
        String imageUrl = blog.getFeaturedImage();
        if (imageUrl != null && !imageUrl.startsWith("http")) {
            imageUrl = "http://localhost:8080" + imageUrl;
        }

        return new BlogResponse(
            blog.getId(),
            blog.getTitle(),
            blog.getSlug(),
            blog.getSummary(),
            blog.getContent(),
            blog.getAuthor(),
            imageUrl,
            blog.getCategory(),
            blog.getStatus(),
            blog.getPublishedAt(),
            blog.getViewCount(),
            blog.getCreatedAt(),
            blog.getUpdatedAt()
        );
    }
}

