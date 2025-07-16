package com.harsahaat.controller;

import com.harsahaat.domain.USER_ROLE;
import com.harsahaat.model.User;
import com.harsahaat.response.AuthResponse;
import com.harsahaat.response.SignupRequest;
import com.harsahaat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String jwt
          ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }
}
