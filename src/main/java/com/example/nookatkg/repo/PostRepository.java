package com.example.nookatkg.repo;

import com.example.nookatkg.model.Category;
import com.example.nookatkg.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryOrderByDateDesc(Category category);
}
