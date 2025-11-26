package com.samreact.GoldenRoyalEmail.service;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import com.samreact.GoldenRoyalEmail.data.model.Blog;
import com.samreact.GoldenRoyalEmail.data.repository.BlogRepository;
import com.samreact.GoldenRoyalEmail.dto.request.BlogRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@SuppressWarnings("NullableProblems")
public class BlogServiceImpl implements BlogService {
    
    private final BlogRepository blogRepository;
    
    @Override
    @Transactional
    public Blog createBlog(BlogRequest request) {
        log.info("Creating new blog with slug: {}", request.slug());
        
        if (blogRepository.existsBySlug(request.slug())) {
            throw new IllegalArgumentException("Blog with slug '" + request.slug() + "' already exists");
        }
        
        Blog blog = Blog.builder()
            .title(request.title())
            .slug(request.slug())
            .summary(request.summary())
            .content(request.content())
            .author(request.author())
            .featuredImage(request.featuredImage())
            .category(request.category())
            .status(request.status() != null ? request.status() : BlogStatus.DRAFT)
            .build();
        
        Blog savedBlog = blogRepository.save(blog);
        log.info("Blog created successfully with id: {}", savedBlog.getId());
        return savedBlog;
    }
    
    @Override
    @Transactional
    public Blog updateBlog(Long id, BlogRequest request) {
        log.info("Updating blog with id: {}", id);
        
        Blog blog = getBlogById(id);
        
        if (!blog.getSlug().equals(request.slug()) && 
            blogRepository.existsBySlugAndIdNot(request.slug(), id)) {
            throw new IllegalArgumentException("Blog with slug '" + request.slug() + "' already exists");
        }
        
        blog.setTitle(request.title());
        blog.setSlug(request.slug());
        blog.setSummary(request.summary());
        blog.setContent(request.content());
        blog.setAuthor(request.author());
        blog.setFeaturedImage(request.featuredImage());
        blog.setCategory(request.category());
        
        if (request.status() != null) {
            blog.setStatus(request.status());
        }
        
        Blog updatedBlog = blogRepository.save(blog);
        log.info("Blog updated successfully with id: {}", updatedBlog.getId());
        return updatedBlog;
    }
    
    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Blog not found with id: " + id));
    }
    
    @Override
    public Blog getBlogBySlug(String slug) {
        return blogRepository.findBySlug(slug)
            .orElseThrow(() -> new EntityNotFoundException("Blog not found with slug: " + slug));
    }
    
    @Override
    @Transactional
    public void deleteBlog(Long id) {
        log.info("Deleting blog with id: {}", id);
        Blog blog = getBlogById(id);
        blogRepository.delete(blog);
        log.info("Blog deleted successfully with id: {}", id);
    }
    
    @Override
    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
    
    @Override
    public Page<Blog> getBlogsByStatus(BlogStatus status, Pageable pageable) {
        return blogRepository.findByStatus(status, pageable);
    }
    
    @Override
    public Page<Blog> getBlogsByCategory(BlogCategory category, Pageable pageable) {
        return blogRepository.findByCategory(category, pageable);
    }
    
    @Override
    public Page<Blog> searchBlogs(String keyword, Pageable pageable) {
        return blogRepository.searchByKeyword(keyword, pageable);
    }
    
    @Override
    @Transactional
    public Blog publishBlog(Long id) {
        log.info("Publishing blog with id: {}", id);
        Blog blog = getBlogById(id);
        blog.publish();
        Blog publishedBlog = blogRepository.save(blog);
        log.info("Blog published successfully with id: {}", publishedBlog.getId());
        return publishedBlog;
    }
    
    @Override
    @Transactional
    public Blog archiveBlog(Long id) {
        log.info("Archiving blog with id: {}", id);
        Blog blog = getBlogById(id);
        blog.archive();
        Blog archivedBlog = blogRepository.save(blog);
        log.info("Blog archived successfully with id: {}", archivedBlog.getId());
        return archivedBlog;
    }
    
    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        log.debug("Incrementing view count for blog with id: {}", id);
        Blog blog = getBlogById(id);
        blog.incrementViewCount();
        blogRepository.save(blog);
    }
    
    @Override
    public List<Blog> getTopViewedBlogs(int limit) {
        return blogRepository.findTop10ByStatusOrderByViewCountDesc(BlogStatus.PUBLISHED);
    }
    
    @Override
    public Page<Blog> getRecentPublishedBlogs(Pageable pageable) {
        return blogRepository.findRecentPublished(BlogStatus.PUBLISHED, pageable);
    }
}

