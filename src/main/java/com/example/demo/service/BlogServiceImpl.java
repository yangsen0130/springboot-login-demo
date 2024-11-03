

package com.example.demo.service;

import com.example.demo.model.Blog;
import com.example.demo.model.User;
import com.example.demo.repository.BlogRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Blog createBlog(String title, MultipartFile markdownFile, Long userId) {
        try {
            User author = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("用户不存在"));

            String content = new String(markdownFile.getBytes(), StandardCharsets.UTF_8);

            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setAuthor(author);

            return blogRepository.save(blog);
        } catch (IOException e) {
            throw new RuntimeException("文件读取失败");
        }
    }

    @Override
    public Blog updateBlog(Long blogId, String title, MultipartFile markdownFile, Long userId) {
        try {
            Blog blog = blogRepository.findById(blogId)
                    .orElseThrow(() -> new RuntimeException("博客不存在"));

            if (!blog.getAuthor().getId().equals(userId)) {
                throw new RuntimeException("无权修改此博客");
            }

            String content = new String(markdownFile.getBytes(), StandardCharsets.UTF_8);
            blog.setTitle(title);
            blog.setContent(content);

            return blogRepository.save(blog);
        } catch (IOException e) {
            throw new RuntimeException("文件读取失败");
        }
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