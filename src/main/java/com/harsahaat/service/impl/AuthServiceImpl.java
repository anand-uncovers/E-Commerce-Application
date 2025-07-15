package com.harsahaat.service.impl;

import com.harsahaat.config.JwtProvider;
import com.harsahaat.domain.USER_ROLE;
import com.harsahaat.model.Cart;
import com.harsahaat.model.User;
import com.harsahaat.model.VerificationCode;
import com.harsahaat.repository.CartRepository;
import com.harsahaat.repository.UserRepository;
import com.harsahaat.repository.VerificationCodeRepository;
import com.harsahaat.request.LoginRequest;
import com.harsahaat.response.AuthResponse;
import com.harsahaat.response.SignupRequest;
import com.harsahaat.service.AuthService;
import com.harsahaat.service.EmailService;
import com.harsahaat.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;


    @Override
    public AuthResponse signin(LoginRequest req) {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username,otp);
        System.out.println("LASTrsyrsyhs--------------------");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Logged in Successfully");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName= authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        System.out.println("a.authenticate 1");
        UserDetails userDetails= customUserService.loadUserByUsername(username);

        System.out.println("b.authenticate 2");
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username ");
        }

        System.out.println("c.authenticate 3");
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);
        System.out.println("d.authenticate 4");

        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            System.out.println("e.authenticate 5");
            throw new BadCredentialsException(" Wrong OTP ");
        }

        System.out.println("f.before return authenticate");
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }



    @Override
    public void sentLoginOtp(String email) throws Exception {
        String SIGNIN_PREFIX = "signin_";

        if(email.startsWith(SIGNIN_PREFIX)){
            email=email.substring(SIGNIN_PREFIX.length());

            User user = userRepository.findByEmail(email);
            if(user==null){
                throw new Exception("User doesn't exist with provided mail id");
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if(isExist!=null){
            verificationCodeRepository.delete(isExist);
        }
        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject ="Harsa Haat login/signup otp";
        String text="your login/signup otp is - " + otp;

        emailService.sendVerificationOtpEmail(email,otp,subject,text);
    }


    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong OTP...");
        }
        User user= userRepository.findByEmail(req.getEmail());

        if(user==null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("8964765423");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));


        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}
