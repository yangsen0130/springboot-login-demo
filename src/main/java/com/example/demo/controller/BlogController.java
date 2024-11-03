package com.example.demo.controller;

import com.example.demo.dto.BlogCreateDTO;
import com.example.demo.dto.BlogUpdateDTO;
import com.example.demo.model.Blog;
import com.example.demo.model.Result;
import com.example.demo.model.User;
import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    public Result<Blog> createBlog(@RequestBody BlogCreateDTO blogDTO, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Blog blog = blogService.createBlog(blogDTO, user.getId());
            return Result.success(blog);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{blogId}")
    public Result<Blog> updateBlog(@PathVariable Long blogId,
                                   @RequestBody BlogUpdateDTO blogDTO,
                                   HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Blog blog = blogService.updateBlog(blogId, blogDTO, user.getId());
            return Result.success(blog);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{blogId}")
    public Result<Void> deleteBlog(@PathVariable Long blogId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            blogService.deleteBlog(blogId, user.getId());
            return Result.success(null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{blogId}")
    public Result<Blog> getBlog(@PathVariable Long blogId) {
        try {
            Blog blog = blogService.getBlog(blogId);
            return Result.success(blog);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<Page<Blog>> listBlogs(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Blog> blogs = blogService.listBlogs(page, size);
            return Result.success(blogs);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/user")
    public Result<Page<Blog>> listUserBlogs(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Page<Blog> blogs = blogService.listUserBlogs(user.getId(), page, size);
            return Result.success(blogs);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}