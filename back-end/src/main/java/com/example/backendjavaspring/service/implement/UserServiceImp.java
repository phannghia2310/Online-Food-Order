package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.entity.User;
import com.example.backendjavaspring.repository.UserRepository;
import com.example.backendjavaspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.backendjavaspring.model.Constants.ROLE_ADMIN_ID;
import static com.example.backendjavaspring.model.Constants.ROLE_USER_ID;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUserCustomer() {
        return userRepository.findAllByRoleRoleId(ROLE_USER_ID);
    }

    public User createUser(User user){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public User findUserById(String email){
        return userRepository.findById(email).orElse(null);
    }

    public boolean isUserEmailExist(String email){
        return userRepository.findUserByEmailIdEquals(email) != null;
    }




}
