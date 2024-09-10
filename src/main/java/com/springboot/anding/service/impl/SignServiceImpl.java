package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.sign.CommonResponse;
import com.springboot.anding.data.dto.sign.SignInResultDto;
import com.springboot.anding.data.dto.sign.SignUpDto;
import com.springboot.anding.data.dto.sign.SignUpResultDto;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {

    private Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository , JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;

    }
    @Override
    public boolean checkLoginIdDuplicate(String account) {
        return userRepository.existsByAccount(account);
    }
    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public SignUpResultDto SignUp(SignUpDto signUpDto, String roles) {
        User user;
        if(roles.equalsIgnoreCase("admin")){
            user = User.builder()
                    .account(signUpDto.getAccount())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .name(signUpDto.getName())
                    .gender(signUpDto.getGender())
                    .phone(signUpDto.getPhone())
                    .nickname(signUpDto.getNickname())
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }else{
            user = User.builder()
                    .account(signUpDto.getAccount())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .name(signUpDto.getName())
                    .gender(signUpDto.getGender())
                    .phone(signUpDto.getPhone())
                    .nickname(signUpDto.getNickname())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
        User saveUser = userRepository.save(user);

        SignUpResultDto signUpResultDto = new SignUpResultDto();
        logger.info("[getSignResultDto] USER 정보 들어왔는지 확인 후 결과값 주입");

        if(!saveUser.getAccount().isEmpty()){
            setSucces(signUpResultDto);
        }else{
            setFail(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignInResultDto SignIn(String account, String password) throws RuntimeException {
        logger.info("[getSignInResult] signDataHandler로 회원정보 요청");
        User user = userRepository.getByAccount(account);
        logger.info("[getSignInResult] ACCOUNT:{}",account);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException();
        }
        logger.info("[getSignInResult] 패스워드 일치");

        logger.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getAccount()),
                        user.getRoles()))
                .build();
        logger.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSucces(signInResultDto);
        return signInResultDto;
    }

    private void setSucces(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFail(SignUpResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.Fail.getCode());
        result.setMsg(CommonResponse.Fail.getMsg());

    }
}