package com.samreact.GoldenRoyalEmail.data.repository;

import com.samreact.GoldenRoyalEmail.data.enums.BlogCategory;
import com.samreact.GoldenRoyalEmail.data.enums.BlogStatus;
import com.samreact.GoldenRoyalEmail.data.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
@SuppressWarnings("NullableProblems")
public interface BlogRepository extends JpaRepository<Blog, Long> {
    

    Optional<Blog> findBySlug(String slug);
    

    Page<Blog> findByStatus(BlogStatus status, Pageable pageable);
    

    Page<Blog> findByCategory(BlogCategory category, Pageable pageable);

    Page<Blog> findByStatusAndCategory(BlogStatus status, BlogCategory category, Pageable pageable);

    Page<Blog> findByStatusOrderByPublishedAtDesc(BlogStatus status, Pageable pageable);

    Page<Blog> findByAuthor(String author, Pageable pageable);
    

    @Query("SELECT b FROM Blog b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Blog> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    

    @Query("SELECT b FROM Blog b WHERE b.status = :status AND (" +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.summary) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Blog> searchPublishedByKeyword(@Param("status") BlogStatus status, 
                                         @Param("keyword") String keyword, 
                                         Pageable pageable);


    List<Blog> findTop10ByStatusOrderByViewCountDesc(BlogStatus status);
    

    @Query("SELECT b FROM Blog b WHERE b.status = :status AND b.publishedAt IS NOT NULL " +
           "ORDER BY b.publishedAt DESC")
    Page<Blog> findRecentPublished(@Param("status") BlogStatus status, Pageable pageable);

    long countByStatus(BlogStatus status);

    long countByCategory(BlogCategory category);

    boolean existsBySlug(String slug);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Blog b " +
           "WHERE b.slug = :slug AND b.id != :id")
    boolean existsBySlugAndIdNot(@Param("slug") String slug, @Param("id") Long id);

    @Modifying
    @Query("UPDATE Blog b SET b.viewCount = b.viewCount + 1 WHERE b.id = :id")
    void incrementViewCount(@Param("id") Long id);
    

    @Query("SELECT b FROM Blog b WHERE b.status = :status AND " +
           "b.publishedAt BETWEEN :startDate AND :endDate " +
           "ORDER BY b.publishedAt DESC")
    Page<Blog> findPublishedBetweenDates(@Param("status") BlogStatus status,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate,
                                          Pageable pageable);
}

