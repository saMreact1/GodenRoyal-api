# Blog API Documentation

## Overview
Enterprise-grade Blog Management System with MySQL persistence, built with Spring Boot 4.0 and Java 21.

## Database Setup

### MySQL Configuration
```bash
# Create database
CREATE DATABASE goldenroyal_db;

# Or use environment variables
export DB_URL=jdbc:mysql://localhost:3306/goldenroyal_db
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

## API Endpoints

### Create Blog
```http
POST /api/blogs
Content-Type: application/json

{
  "title": "Getting Started with Spring Boot",
  "slug": "getting-started-spring-boot",
  "summary": "A comprehensive guide to Spring Boot development",
  "content": "Full blog content here...",
  "author": "John Doe",
  "featuredImage": "https://example.com/image.jpg",
  "category": "TECHNOLOGY",
  "status": "DRAFT"
}
```

### Update Blog
```http
PUT /api/blogs/{id}
Content-Type: application/json
```

### Get Blog by ID
```http
GET /api/blogs/{id}
```

### Get Blog by Slug (increments view count)
```http
GET /api/blogs/slug/{slug}
```

### Delete Blog
```http
DELETE /api/blogs/{id}
```

### Get All Blogs (Paginated)
```http
GET /api/blogs?page=0&size=10&sortBy=createdAt&direction=DESC
```

### Get Blogs by Status
```http
GET /api/blogs/status/PUBLISHED?page=0&size=10
```

### Get Blogs by Category
```http
GET /api/blogs/category/TECHNOLOGY?page=0&size=10
```

### Search Blogs
```http
GET /api/blogs/search?keyword=spring&page=0&size=10
```

### Publish Blog
```http
POST /api/blogs/{id}/publish
```

### Archive Blog
```http
POST /api/blogs/{id}/archive
```

### Get Top Viewed Blogs
```http
GET /api/blogs/top-viewed?limit=10
```

### Get Recent Published Blogs
```http
GET /api/blogs/recent?page=0&size=10
```

## Enums

### BlogStatus
- `DRAFT` - Blog is in draft state
- `PUBLISHED` - Blog is published and visible
- `ARCHIVED` - Blog is archived

### BlogCategory
- `TECHNOLOGY`
- `BUSINESS`
- `LIFESTYLE`
- `EDUCATION`
- `HEALTH`
- `FINANCE`
- `TRAVEL`
- `FOOD`
- `ENTERTAINMENT`
- `OTHER`

## Features

✅ Full CRUD operations  
✅ Pagination and sorting  
✅ Search functionality  
✅ View count tracking  
✅ Status management (Draft/Published/Archived)  
✅ Category filtering  
✅ Slug-based URLs  
✅ Audit timestamps (createdAt, updatedAt)  
✅ Input validation  
✅ Global exception handling  
✅ MySQL persistence with JPA/Hibernate  
✅ Optimized database indexes  

## Database Schema

The `blogs` table includes:
- Indexed columns: status, category, slug, published_at, created_at
- Unique constraint on slug
- Audit fields with automatic timestamps
- View count tracking
- Rich text content support

