package com.springboot.anding.service;

import com.springboot.anding.data.dto.response.ResponseUserDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    ResponseUserDto getUser(HttpServletRequest servletRequest);
}
