package com.springboot.anding.service.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFifteenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenListDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveListDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FifteenService {

    ResponseFifteenDto saveFifteen(RequestFifteenDto requestFifteenDto,
                                     HttpServletRequest httpServletRequest);
    ResponseFifteenDto getFifteen(Long fifteen_id)throws Exception;
    ResponseFifteenListDto getFifteenList(HttpServletRequest servletRequest,
                                       HttpServletResponse servletResponse);
    void deleteFifteen(Long fifteen_id,
                    HttpServletRequest httpServletRequest) throws Exception;
}
