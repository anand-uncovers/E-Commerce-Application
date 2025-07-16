package com.harsahaat.controller;

import com.harsahaat.model.VerificationCode;
import com.harsahaat.repository.VerificationCodeRepository;
import com.harsahaat.request.LoginRequest;
import com.harsahaat.response.ApiResponse;
import com.harsahaat.response.AuthResponse;
import com.harsahaat.service.AuthService;
import com.harsahaat.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(
            @RequestBody LoginRequest req
            ) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();

//        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
//        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
//            throw new Exception("wrong otp...");
//        }
        req.setEmail("seller_"+ email);
        AuthResponse authResponse = authService.signin(req);

        return ResponseEntity.ok(authResponse);
    }
}
