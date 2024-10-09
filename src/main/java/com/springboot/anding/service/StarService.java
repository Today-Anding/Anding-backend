package com.springboot.anding.service;

import com.springboot.anding.data.dto.request.RequestStarDto;
import com.springboot.anding.data.dto.response.ResponseStarListDto;
import com.springboot.anding.data.entity.Star;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StarService {
    boolean addStarForFive(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception;

    boolean addStarForTen(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception;

    boolean addStarForFifteen(RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception;

    ResponseStarListDto getTop4RecentStar(HttpServletRequest httpServletRequest);


    //long countStarForStory5(Long story5Id);
}
