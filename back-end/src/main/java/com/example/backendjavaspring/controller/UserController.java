package com.example.backendjavaspring.controller;

import com.example.backendjavaspring.model.dto.ChangePasswordDTO;
import com.example.backendjavaspring.model.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public interface UserController {

    @PostMapping("/register")
    ResponseEntity<?> addUser(@Validated @ModelAttribute UserRequestDTO userRequest);

    @GetMapping("/get_user")
    ResponseEntity<?> getUser(Authentication authentication);

    @GetMapping("/get_all_bill")
    ResponseEntity<?> getAllBillByUser(Authentication authentication);

    @GetMapping("/get_all_user")
    ResponseEntity<?> getAllUser();

    @PutMapping("/update_user")
    ResponseEntity<?> updateUser(@Validated @RequestBody UserRequestDTO userRequest);

    @PutMapping("/change_password")
    ResponseEntity<?> changePassword(@Validated @RequestBody ChangePasswordDTO changePasswordDTO);
}
