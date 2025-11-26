package com.samreact.GoldenRoyalEmail.data.model;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
    name = "blogs",
    indexes = {
        @Index(name = "idx_blog_status", columnList = "status"),
        @Index(name = "idx_blog_category", columnList = "category"),
        @Index(name = "idx_blog_slug", columnList = "slug", unique = true),
        @Index(name = "idx_blog_published_at", columnList = "published_at"),
        @Index(name = "idx_blog_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String title;
    
    @NotBlank(message = "Slug is required")
    @Size(max = 250, message = "Slug must not exceed 250 characters")
    @Column(nullable = false, unique = true, length = 250)
    private String slug;
    
    @NotBlank(message = "Summary is required")
    @Size(max = 500, message = "Summary must not exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String summary;
    
    @NotBlank(message = "Content is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String author;
    
    @Size(max = 500, message = "Featured image URL must not exceed 500 characters")
    @Column(name = "featured_image", length = 500)
    private String featuredImage;
    
    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BlogCategory category;
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BlogStatus status = BlogStatus.DRAFT;
    
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Long viewCount = 0L;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    

    public void publish() {
        this.status = BlogStatus.PUBLISHED;
        if (this.publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        }
    }
    

    public void archive() {
        this.status = BlogStatus.ARCHIVED;
    }
    

    public void incrementViewCount() {
        this.viewCount++;
    }

    public boolean isPublished() {
        return this.status == BlogStatus.PUBLISHED;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id) && Objects.equals(slug, blog.slug);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, slug);
    }
    
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", author='" + author + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", publishedAt=" + publishedAt +
                ", viewCount=" + viewCount +
                '}';
    }
}

