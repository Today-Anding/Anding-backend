package com.springboot.anding.service.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFiveDto;
import com.springboot.anding.data.dto.request.synopsis.RequestTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveListDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenListDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TenService {
    ResponseTenDto saveTen(RequestTenDto requestTenDto,
                           HttpServletRequest httpServletRequest);
    ResponseTenDto getTen(Long ten_id) throws Exception;
    ResponseTenListDto getTenList(HttpServletRequest servletRequest,
                                  HttpServletResponse servletResponse);
    void deleteTen(Long ten_id,
                    HttpServletRequest httpServletRequest) throws Exception;
}
