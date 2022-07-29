package com.example.nookatkg.controller;


import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Comment;
import com.example.nookatkg.service.impl.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;

    @PostMapping("/comment/{postId}")
    public String createPost(@ModelAttribute("comment") Comment comment, @PathVariable("postId") Long postId){
        commentServiceImp.createComment(comment.getContent(),postId);
        System.out.println("Comment" + comment.getContent());
        return "redirect:/category/news/"+postId;
    }
}
