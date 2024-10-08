package com.springboot.anding.service;

import com.springboot.anding.data.dto.request.RequestStarDto;

import javax.servlet.http.HttpServletRequest;

public interface StarService {
    boolean addStarForFive(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception;

    boolean addStarForTen(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception;

    boolean addStarForFifteen(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception;

    //long countStarForStory5(Long story5Id);
}
