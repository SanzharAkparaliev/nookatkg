package com.example.nookatkg.repo;

import com.example.nookatkg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}