package com.example.nookatkg.service.impl;

import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.repo.PostRepository;
import com.example.nookatkg.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Override
    public void creatPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post, Long postId) {
        Post postInDb = postRepository.findById(postId).get();
        postInDb.setCategory(post.getCategory());
        postInDb.setTitle(post.getTitle());
        postInDb.setContent(post.getContent());
        postInDb.setImageUrl(post.getImageUrl());

        postRepository.save(postInDb);
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostByCategory(Category category) {
        return postRepository.findByCategoryOrderByDateDesc(category);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}