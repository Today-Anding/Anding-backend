package com.springboot.anding.service.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FiveService {
    ResponseFiveDto saveFive(RequestFiveDto requestFiveDto,
                             HttpServletRequest httpServletRequest);
    ResponseFiveDto getFive(Long five_id)throws Exception;
    ResponseFiveDto getFiveList(Long five_id,HttpServletRequest servletRequest,
                                        HttpServletResponse servletResponse);
    //완결된 글만 가져오는 리스트 = is_finished= true인 것만
    void deleteFive(Long five_id,
                    HttpServletRequest httpServletRequest) throws Exception;
}
