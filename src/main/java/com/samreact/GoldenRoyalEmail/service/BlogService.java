package com.samreact.GoldenRoyalEmail.service;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import com.samreact.GoldenRoyalEmail.data.model.Blog;
import com.samreact.GoldenRoyalEmail.dto.request.BlogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SuppressWarnings("NullableProblems")
public interface BlogService {
    Blog createBlog(BlogRequest request);
    Blog updateBlog(Long id, BlogRequest request, MultipartFile image);
    Blog getBlogById(Long id);
    Blog getBlogBySlug(String slug);
    void deleteBlog(Long id);
    Page<Blog> getAllBlogs(Pageable pageable);
    Page<Blog> getBlogsByStatus(BlogStatus status, Pageable pageable);
    Page<Blog> getBlogsByCategory(BlogCategory category, Pageable pageable);
    Page<Blog> searchBlogs(String keyword, Pageable pageable);
    Blog publishBlog(Long id);
    Blog archiveBlog(Long id);
    Blog incrementViewCount(Long id);
    List<Blog> getTopViewedBlogs(int limit);
    Page<Blog> getRecentPublishedBlogs(Pageable pageable);

    String upload(MultipartFile file);
}

