package com.example.nookatkg.controller;


import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.service.CategoryService;
import com.example.nookatkg.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title","Ноокат Таңы");
        model.addAttribute("title_content","Акыркы кабарлар");
        model.addAttribute("categories",categoryService.getCategories());

        Category category = categoryService.getCategory(1L).get();
        Page<Post> posts = postService.getPostByCategory(category);

        List<Post> postList = postService.getPosts();
        List<Post> array = new ArrayList<>();

        if(postList.size() >= 3){
            array.add(postList.get(postList.size()-1));
            array.add(postList.get(postList.size()-2));
            array.add(postList.get(postList.size()-3));
        }
        model.addAttribute("posts",posts);
        model.addAttribute("postList",array);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String getPostsByCetegory(@PathVariable("id") Long id,Model model){
        if(id==1){
            return "redirect:/";
        }
        Category category = categoryService.getCategory(id).get();
        if(category == null){
            return "404";
        }else {
            Page<Post> posts = postService.getPostByCategory(category);
            model.addAttribute("posts",posts);
            model.addAttribute("title",category.getName());
            model.addAttribute("categories",categoryService.getCategories());
            return "catpost";
        }
    }

    @GetMapping("/category/news/{id}")
    public String getNews(@PathVariable("id") Long id,Model model){
        Post post = postService.getPost(id).get();
        Category category = post.getCategory();
        model.addAttribute("post",post);
        model.addAttribute("title",post.getTitle());
        model.addAttribute("categories",categoryService.getCategories());
        return "value";
    }
}
