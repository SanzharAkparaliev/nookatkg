package com.example.nookatkg.repo;

import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findByCategoryOrderByDateDesc(Category category, Pageable pageable);

    List<Post> findAll();
    List<Post> findAllByOrderByDateDesc();

    Optional<Post> findByBannerTrue();
}
