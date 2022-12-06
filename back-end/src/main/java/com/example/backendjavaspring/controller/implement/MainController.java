package com.example.backendjavaspring.controller.implement;

import com.example.backendjavaspring.model.entity.Role;
import com.example.backendjavaspring.model.entity.User;
import com.example.backendjavaspring.service.RoleService;
import com.example.backendjavaspring.service.UserService;
import com.example.backendjavaspring.service.implement.RoleServiceImp;
import com.example.backendjavaspring.service.implement.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

import static com.example.backendjavaspring.model.Constants.ROLE_ADMIN_ID;

@RestController
public class MainController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public ResponseEntity<?> home() {
        Role role = roleService.findRoleById(ROLE_ADMIN_ID);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        if(role == null){
            roleService.createRole(new Role("ADMIN"));
            roleService.createRole(new Role("USER"));
        }
        userService.createUser(new User("admin@gmail.com", "Cao Hoài Nhiên", "1", "HCM", "0123456789", role, timestamp));
        return ResponseEntity.ok("Welcome to Hoai Nhien Furniture!");
    }


}
