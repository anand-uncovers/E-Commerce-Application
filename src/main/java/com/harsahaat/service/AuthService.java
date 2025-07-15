package com.harsahaat.service;

import com.harsahaat.request.LoginRequest;
import com.harsahaat.response.AuthResponse;
import com.harsahaat.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signin(LoginRequest req) ;
}
