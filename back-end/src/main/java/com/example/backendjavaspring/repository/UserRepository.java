package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmailIdEquals(String email);
    List<User> findAllByRoleRoleId(long id);
}
