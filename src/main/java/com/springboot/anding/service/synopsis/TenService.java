package com.springboot.anding.service.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFiveDto;
import com.springboot.anding.data.dto.request.synopsis.RequestTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenDto;

import javax.servlet.http.HttpServletRequest;

public interface TenService {
    ResponseTenDto saveTen(RequestTenDto requestTenDto,
                           HttpServletRequest httpServletRequest);

    ResponseTenDto getTen(Long ten_id);
    void deleteTen(Long ten_id,
                    HttpServletRequest httpServletRequest) throws Exception;
}
