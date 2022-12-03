package com.example.backendjavaspring.controller.implement;

import com.example.backendjavaspring.authority.JwtTokenProvider;
import com.example.backendjavaspring.authority.MyUserDetail;
import com.example.backendjavaspring.controller.LoginController;
import com.example.backendjavaspring.model.dto.LoginRequestDTO;
import com.example.backendjavaspring.model.dto.LoginResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginControllerImp implements LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginControllerImp.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public LoginControllerImp(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
            logger.debug("INFO AUTHENTICATION: " + myUserDetail.getUsername());

            final String token = tokenProvider.generateToken(myUserDetail);

            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
        } catch (Exception e) {
            logger.error("Login Controller error: ",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LOGIN FAIL!");
        }
    }


    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }



}
