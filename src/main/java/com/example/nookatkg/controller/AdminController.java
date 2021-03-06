package com.example.nookatkg.controller;


import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.model.User;
import com.example.nookatkg.service.impl.CategoryServiceImpl;
import com.example.nookatkg.service.impl.CommentServiceImp;
import com.example.nookatkg.service.impl.PostServiceImpl;
import com.example.nookatkg.service.impl.UserServiceimpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryServiceImpl categoryService;
    public static  String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

    @Autowired
    private UserServiceimpl userServiceimpl;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private CommentServiceImp commentServiceImp;
    @GetMapping("/")
    public String AdminPage(Model model){
        model.addAttribute("title","Админ панели");
        return "admin/admin-dashboard";
    }

    @GetMapping("/categories")
    public String CategoryPage(Model model){
        model.addAttribute("title","Категориялар");
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories",categories);
        return "admin/categories";
    }

    @GetMapping("/add-category")
    public String Category(Model model){
        model.addAttribute("title","Категория кошуу");
        model.addAttribute("category",new Category());
        return "admin/add-category";
    }
    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") Category category){
        String[] arrOfStr = category.getName().split(" ");
        if(arrOfStr.length > 1){
            System.out.println("true");
            String newCategory = arrOfStr[0] + "_"+arrOfStr[1];
            category.setName(newCategory.toUpperCase());
        }else {
            category.setName(category.getName().toUpperCase());
        }
        categoryService.createCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories";
    }

    @GetMapping("category/update/{id}")
    public String updateCategory(@PathVariable("id")Long categoryId,Model model){
        Optional<Category> category = categoryService.getCategory(categoryId);
        model.addAttribute("title",category.get().getName());
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "admin/update-category";
        }else {
            return "404";
        }
    }

    @PostMapping("/update-category")
    public String updateCategoru(@ModelAttribute("category")Category category){
        Category category1 = categoryService.getCategory(category.getId()).get();
        category1.setName(category.getName());
        categoryService.createCategory(category1);
        return "redirect:/admin/categories";
    }

    @GetMapping("/add-news")
    public String createNews(Model model){
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("title","Жанылык жазуу");
        model.addAttribute("categories",categories);
        return "admin/add-news";
    }


    @PostMapping("/add-post")
    public String createPost(@ModelAttribute Post post, @RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {

//            post.setImageUrl(file.getOriginalFilename());
//            File saveFile = new ClassPathResource("/static/img").getFile();
//            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
        String imageUUID;
        if(!file.isEmpty()){
//            imageUUID = file.getOriginalFilename();
//            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
//            Files.write(fileNameAndPath,file.getBytes());
//            post.setImageUrl(imageUUID);

            File saveFile  = new ClassPathResource("static/uploads").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            post.setImageUrl(file.getOriginalFilename());
        }else {
            post.setImageUrl("image.jpg");
        }
        post.setDate(new Date());
        postService.creatPost(post);
        return "redirect:/admin/add-news";
    }
    @GetMapping("/news/{id}")
    public String getPost(Model model,@PathVariable("id") Long postId){
        Post post = postService.getPost(postId).get();
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("title","Жанылоо");
        model.addAttribute("post",post);
        model.addAttribute("categories",categories);
        return "admin/update-news";
    }

    @PostMapping("/update-post")
    public String updatePost(Model model, @ModelAttribute Post post,@RequestParam("image") MultipartFile file) throws IOException {
        postService.updatePost(post,post.getId(),file);
        return "redirect:/";
    }
    @GetMapping("/news/delete/{id}")
    public String deletePost(@PathVariable("id")Long id){
        Post post = postService.getPost(id).get();
        Category category = post.getCategory();
        postService.deletePost(id);

        return "redirect:/";
    }
    @GetMapping("/banner")
    public String Banner(Model model){
        model.addAttribute("title","Баннер");
        List<Post> posts = postService.findAllByDate();
        model.addAttribute("posts",posts);
        System.out.println(posts);
        return "admin/bannerSwicth";
    }

    @GetMapping("/post/banner/active/{id}")
    public String DoActive(@PathVariable("id") Long postId){
        Post post = postService.getPost(postId).get();
        List<Post> posts = postService.getPosts();
        for(Post post1:posts){
            if(post1.isBanner() == true){
                post1.setBanner(false);
                postService.creatPost(post1);
            }
        }

        post.setBanner(true);
        postService.creatPost(post);
        return "redirect:/admin/banner";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = userServiceimpl.getAllUsers();
        model.addAttribute("users",users);
        return "admin/users";
    }

    @GetMapping("/comment/{id}/{postId}")
    public String deleteComment(Model model,@ModelAttribute("id") Long id,@ModelAttribute("postId")Long postId){
        commentServiceImp.deleteComment(id);
         return "redirect:/category/news/"+postId;
    }

    @GetMapping("/user/active/{id}")
    public String doAdmin(@PathVariable("id") Long userID){
        userServiceimpl.doAdmin(userID);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/dontactive/{id}")
    public String dontAdmin(@PathVariable("id") Long userID){
        userServiceimpl.dontAdmin(userID);
        return "redirect:/admin/users";
    }



}
