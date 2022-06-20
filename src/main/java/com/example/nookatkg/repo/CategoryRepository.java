package com.example.nookatkg.repo;

import com.example.nookatkg.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
