package com.example.demo.service;

import com.example.demo.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {
    Blog createBlog(String title, MultipartFile markdownFile, Long userId);
    Blog updateBlog(Long blogId, String title, MultipartFile markdownFile, Long userId);
    void deleteBlog(Long blogId, Long userId);
    Blog getBlog(Long blogId);
    Page<Blog> listBlogs(int page, int size);
    Page<Blog> listUserBlogs(Long userId, int page, int size);
}