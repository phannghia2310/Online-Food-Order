package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRole();
    Role createRole(Role role);
    Role updateRole(Role role);
    void deleteRole(long id);
    Role findRoleById(long id);
}
