package com.samreact.GoldenRoyalEmail.controller;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import com.samreact.GoldenRoyalEmail.data.model.Blog;
import com.samreact.GoldenRoyalEmail.dto.request.BlogRequest;
import com.samreact.GoldenRoyalEmail.dto.response.BlogResponse;
import com.samreact.GoldenRoyalEmail.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("NullableProblems")
public class BlogController {
    
    private final BlogService blogService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<BlogResponse> createBlog(@ModelAttribute BlogRequest request) {

        Blog blog = blogService.createBlog(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BlogResponse.from(blog));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BlogResponse> updateBlog(
//            @PathVariable Long id,
//            @Valid @RequestBody BlogRequest request) {
//        log.info("Updating blog with id: {}", id);
//        Blog blog = blogService.updateBlog(id, request);
//        return ResponseEntity.ok(BlogResponse.from(blog));
//    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @RequestPart("data") BlogRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Blog blog = blogService.updateBlog(id, request, image);
        return ResponseEntity.ok(BlogResponse.from(blog));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        return ResponseEntity.ok(BlogResponse.from(blog));
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<BlogResponse> getBlogBySlug(@PathVariable String slug) {
        Blog blog = blogService.getBlogBySlug(slug);
        blogService.incrementViewCount(blog.getId());
        return ResponseEntity.ok(BlogResponse.from(blog));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.info("Deleting blog with id: {}", id);
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<BlogResponse> blogs = blogService.getAllBlogs(pageable)
            .map(BlogResponse::from);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<BlogResponse> viewBlog(@PathVariable Long id) {
        Blog blog = blogService.incrementViewCount(id);
        return ResponseEntity.ok(BlogResponse.from(blog));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<BlogResponse>> getBlogsByStatus(
            @PathVariable BlogStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BlogResponse> blogs = blogService.getBlogsByStatus(status, pageable)
            .map(BlogResponse::from);
        return ResponseEntity.ok(blogs);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<BlogResponse>> getBlogsByCategory(
            @PathVariable BlogCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BlogResponse> blogs = blogService.getBlogsByCategory(category, pageable)
            .map(BlogResponse::from);
        return ResponseEntity.ok(blogs);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<BlogResponse>> searchBlogs(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BlogResponse> blogs = blogService.searchBlogs(keyword, pageable)
            .map(BlogResponse::from);
        return ResponseEntity.ok(blogs);
    }
    
    @PostMapping("/{id}/publish")
    public ResponseEntity<BlogResponse> publishBlog(@PathVariable Long id) {
        log.info("Publishing blog with id: {}", id);
        Blog blog = blogService.publishBlog(id);
        return ResponseEntity.ok(BlogResponse.from(blog));
    }
    
    @PostMapping("/{id}/archive")
    public ResponseEntity<BlogResponse> archiveBlog(@PathVariable Long id) {
        log.info("Archiving blog with id: {}", id);
        Blog blog = blogService.archiveBlog(id);
        return ResponseEntity.ok(BlogResponse.from(blog));
    }
    
    @GetMapping("/top-viewed")
    public ResponseEntity<List<BlogResponse>> getTopViewedBlogs(
            @RequestParam(defaultValue = "10") int limit) {
        List<BlogResponse> blogs = blogService.getTopViewedBlogs(limit).stream()
            .map(BlogResponse::from)
            .toList();
        return ResponseEntity.ok(blogs);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<Page<BlogResponse>> getRecentPublishedBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogResponse> blogs = blogService.getRecentPublishedBlogs(pageable)
            .map(BlogResponse::from);
        return ResponseEntity.ok(blogs);
    }
}

