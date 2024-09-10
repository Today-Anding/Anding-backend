package com.springboot.anding.service.impl;

import com.springboot.anding.config.security.JwtTokenProvider;
import com.springboot.anding.data.dto.response.ResponseUserDto;
import com.springboot.anding.data.entity.User;
import com.springboot.anding.data.repository.UserRepository;
import com.springboot.anding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public ResponseUserDto getUser(HttpServletRequest servletRequest) {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        String account = jwtTokenProvider.getUsername(token);

        ResponseUserDto responseUserDto = new ResponseUserDto();
        if (jwtTokenProvider.validationToken(token)) {
            User user = userRepository.getByAccount(account);
            responseUserDto.setAccount(user.getAccount());
            responseUserDto.setName(user.getName());
            responseUserDto.setPhone(user.getPhone());
            responseUserDto.setGender(user.getGender());
            responseUserDto.setUid(user.getUid());
            responseUserDto.setNickname(user.getNickname());
        }
        return responseUserDto;
    }
}
