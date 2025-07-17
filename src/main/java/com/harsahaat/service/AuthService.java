package com.harsahaat.service;

import com.harsahaat.domain.USER_ROLE;
import com.harsahaat.request.LoginRequest;
import com.harsahaat.response.AuthResponse;
import com.harsahaat.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signin(LoginRequest req) throws Exception;
}
