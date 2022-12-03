package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.entity.Role;
import com.example.backendjavaspring.repository.RoleRepository;
import com.example.backendjavaspring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRole(){
       return roleRepository.findAll();
    }

    public Role createRole(Role role){
        return roleRepository.save(role);
    }

    public Role updateRole(Role role){
        return roleRepository.save(role);
    }

    public void deleteRole(long id){
        roleRepository.deleteById(id);
    }

    public Role findRoleById(long id){
        return roleRepository.findById(id).orElse(null);
    }
}
