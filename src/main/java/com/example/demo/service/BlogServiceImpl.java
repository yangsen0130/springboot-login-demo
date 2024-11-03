package com.example.demo.service;

import com.example.demo.dto.BlogCreateDTO;
import com.example.demo.dto.BlogUpdateDTO;
import com.example.demo.model.Blog;
import com.example.demo.model.User;
import com.example.demo.repository.BlogRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Blog createBlog(BlogCreateDTO blogDTO, Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(author);

        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long blogId, BlogUpdateDTO blogDTO, Long userId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("博客不存在"));

        if (!blog.getAuthor().getId().equals(userId)) {
            throw new RuntimeException("无权修改此博客");
        }

        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());

        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long blogId, Long userId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("博客不存在"));

        if (!blog.getAuthor().getId().equals(userId)) {
            throw new RuntimeException("无权删除此博客");
        }

        blogRepository.delete(blog);
    }

    @Override
    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("博客不存在"));
    }

    @Override
    public Page<Blog> listBlogs(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createTime").descending());
        return blogRepository.findAll(pageRequest);
    }

    @Override
    public Page<Blog> listUserBlogs(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createTime").descending());
        return blogRepository.findAllByAuthorId(userId, pageRequest);
    }
}