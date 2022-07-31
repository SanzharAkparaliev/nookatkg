package com.example.nookatkg.service;

import com.example.nookatkg.model.Comment;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    void createComment(String content, Long postId, String username);
    void deleteComment(Long commentId);

    List<Comment> getCommentsByPost(Post post);
}
