package com.example.nookatkg.service.impl;

import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import com.example.nookatkg.repo.PostRepository;
import com.example.nookatkg.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public void updatePost(Post post, Long postId, MultipartFile file) throws IOException {
        Post postInDb = postRepository.findById(postId).get();
        postInDb.setCategory(post.getCategory());
        postInDb.setTitle(post.getTitle());
        postInDb.setContent(post.getContent());
        if(!file.isEmpty()){
            //delete old photo
            File deleteFile  = new ClassPathResource("static/img").getFile();
            File file1 = new File(deleteFile,postInDb.getImageUrl());
            file1.delete();
            //update new photo
            File saveFile  = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            postInDb.setImageUrl(file.getOriginalFilename());
        }else{
            postInDb.setImageUrl(postInDb.getImageUrl());
        }
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
    public Page<Post> getPostByCategory(Category category,int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1,8);
        return (Page<Post>) postRepository.findByCategoryOrderByDateDesc(category,pageable);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public  List<Post> findAllByDate(){
        return postRepository.findAllByOrderByDateDesc();
    }

    public Optional<Post> findByBannerTrue(){
        return postRepository.findByBannerTrue();
    }
}
