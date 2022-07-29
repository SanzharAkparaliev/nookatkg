package com.example.nookatkg.repo;

import com.example.nookatkg.model.Comment;
import com.example.nookatkg.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
}
