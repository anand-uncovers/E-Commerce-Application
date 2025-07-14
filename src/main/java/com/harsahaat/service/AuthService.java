package com.harsahaat.service;

import com.harsahaat.response.SignupRequest;

public interface AuthService {

    String createUser(SignupRequest req);
}
