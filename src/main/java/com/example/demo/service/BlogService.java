package com.example.demo.service;

import com.example.demo.dto.BlogCreateDTO;
import com.example.demo.dto.BlogUpdateDTO;
import com.example.demo.model.Blog;
import org.springframework.data.domain.Page;

public interface BlogService {
    Blog createBlog(BlogCreateDTO blogDTO, Long userId);
    Blog updateBlog(Long blogId, BlogUpdateDTO blogDTO, Long userId);
    void deleteBlog(Long blogId, Long userId);
    Blog getBlog(Long blogId);
    Page<Blog> listBlogs(int page, int size);
    Page<Blog> listUserBlogs(Long userId, int page, int size);
}