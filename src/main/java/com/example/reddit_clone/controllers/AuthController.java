package com.example.reddit_clone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.reddit_clone.model.User;
import com.example.reddit_clone.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;



    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpSession session, HttpServletRequest request, Model model) {
        String username = user.getUsername();
        String password = user.getPassword();
        ArrayList<User> userList = userRepository.findByUsernameAndPassword(username, password);
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", "Incorrect Email/password"));
        } else {
            User loggedInUser = userList.get(0);
            session.setAttribute("username", loggedInUser.getUsername());
            model.addAttribute("user", user);
            Map<String, Object> response = new HashMap<>();
            response.put("username", loggedInUser.getUsername());
            response.put("message", "Login Successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                            .body(Collections.singletonMap("message", "Username already exists"));
        }
        else if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                            .body(Collections.singletonMap("message", "Email already exists"));
        }

        try {
            userRepository.save(user);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("message", "User creation failed: " + e.getMessage()));
        }
    }

}

