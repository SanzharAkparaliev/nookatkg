package com.example.nookatkg.controller;


import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.service.CategoryService;
import com.example.nookatkg.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return listByPage(model,1);
    }

    @GetMapping("/category/{id}")
    public String getPostsByCetegory(@PathVariable("id") Long id,Model model){
       int currentPage = 1;
        if(id==1){
            return "redirect:/";
        }
        Category category = categoryService.getCategory(id).get();
        if(category == null){
            return "404";
        }else {
            return listByPages(id,model,currentPage);
        }
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model,@PathVariable("pageNumber") int currentPage){
        Category category = categoryService.getCategory(1L).get();
        Page<Post> posts = postService.getPostByCategory(category,currentPage);
        Long totalItems = posts.getTotalElements();
        int totalPages = posts.getTotalPages();

        List<Post> postList = postService.getPosts();
        List<Post> array = new ArrayList<>();
        if(postList.size() >= 3){
            array.add(postList.get(postList.size()-1));
            array.add(postList.get(postList.size()-2));
            array.add(postList.get(postList.size()-3));
        }
        model.addAttribute("posts",posts);
        model.addAttribute("postList",array);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("title","Ноокат Таңы");
        model.addAttribute("title_content","Акыркы кабарлар");
        model.addAttribute("categories",categoryService.getCategories());

        return "index";
    }
    @GetMapping("category/{id}/pg/{pageNumber}")
    public String listByPages(@PathVariable("id") Long categoryId,Model model,@PathVariable("pageNumber") int currentPage){
        Category category = categoryService.getCategory(categoryId).get();
        Page<Post> posts = postService.getPostByCategory(category,currentPage);
        int totalPages = posts.getTotalPages();


        model.addAttribute("title",category.getName());
        model.addAttribute("posts",posts);
        model.addAttribute("totalPages",totalPages);

        model.addAttribute("currentPage",currentPage);
        model.addAttribute("category",category);
        model.addAttribute("categories",categoryService.getCategories());

        return "catpost";
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
