package com.harsahaat.service.impl;

import com.harsahaat.repository.UserRepository;
import com.harsahaat.response.SignupRequest;
import com.harsahaat.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Override
    public String createUser(SignupRequest req) {
        return "";
    }
}
