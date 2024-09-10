package com.springboot.anding.service;

import com.springboot.anding.data.dto.sign.SignInResultDto;
import com.springboot.anding.data.dto.sign.SignUpDto;
import com.springboot.anding.data.dto.sign.SignUpResultDto;

public interface SignService {
    boolean checkLoginIdDuplicate(String loginId);

    boolean checkNicknameDuplicate(String nickname);

    SignUpResultDto SignUp(SignUpDto signUpDto, String roles);
    SignInResultDto SignIn(String account, String password) throws RuntimeException;
}
