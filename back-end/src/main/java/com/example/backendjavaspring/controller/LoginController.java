package com.example.backendjavaspring.controller;

import com.example.backendjavaspring.model.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginController {

    @PostMapping("/login")
    ResponseEntity<?> login(@Validated @RequestBody LoginRequestDTO loginRequest);

}
