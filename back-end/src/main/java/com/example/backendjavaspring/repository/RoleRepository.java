package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
