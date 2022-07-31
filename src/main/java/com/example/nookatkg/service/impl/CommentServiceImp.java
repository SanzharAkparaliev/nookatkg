package com.example.nookatkg.service.impl;

import com.example.nookatkg.model.Comment;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.model.User;
import com.example.nookatkg.repo.CommentRepo;
import com.example.nookatkg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private UserServiceimpl userServiceimpl;
    @Override
    public void createComment(String content, Long postId,String username) {
      Optional<Post> post =  postService.getPost(postId);
      Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post.get());
        comment.setUsername(username);
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepo.findByPost(post);
    }
}
