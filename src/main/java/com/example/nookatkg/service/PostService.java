package com.example.nookatkg.service;

import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void creatPost(Post post);
    void updatePost(Post post,Long postId);
    Optional<Post> getPost(Long postId);
    List<Post> getPosts();
    Page<Post> getPostByCategory(Category category);

    void deletePost(Long postId);
}
