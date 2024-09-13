package com.springboot.anding.service.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFifteenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenDto;

import javax.servlet.http.HttpServletRequest;

public interface FifteenService {

    ResponseFifteenDto saveFifteen(RequestFifteenDto requestFifteenDto,
                                     HttpServletRequest httpServletRequest);
    ResponseFifteenDto getFifteen(Long fifteen_id);
    void deleteFifteen(Long fifteen_id,
                    HttpServletRequest httpServletRequest) throws Exception;
}
