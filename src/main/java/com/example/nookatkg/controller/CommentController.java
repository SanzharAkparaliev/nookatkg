package com.example.nookatkg.controller;


import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Comment;
import com.example.nookatkg.model.User;
import com.example.nookatkg.repo.UserRepository;
import com.example.nookatkg.service.impl.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/comment/{postId}")
    public String createPost(@ModelAttribute("comment") Comment comment,Principal principal, @PathVariable("postId") Long postId){
        User user = userRepository.getUserByUsername(principal.getName());
        if(user == null){
            commentServiceImp.createComment(comment.getContent(),postId,principal.getName());
        }else {
            commentServiceImp.createComment(comment.getContent(),postId,user.getUsername());
        }
        return "redirect:/category/news/"+postId;
    }


}
