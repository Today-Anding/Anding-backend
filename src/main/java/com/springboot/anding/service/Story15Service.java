package com.springboot.anding.service;

import com.springboot.anding.data.dto.request.RequestStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory15ListDto;

import javax.servlet.http.HttpServletRequest;

public interface Story15Service {
    ResponseStory15Dto saveStory15(RequestStory15Dto requestStory15Dto,
                                   HttpServletRequest httpServletRequest);
    ResponseStory15Dto getStory15(Long fifteen_id, int position)throws Exception;

    ResponseStory15ListDto getCompleteStory15List(Long fifteenId) throws Exception;

    ResponseStory15ListDto getImcompleteStory15List(Long fifteenId) throws Exception;

    void deleteStory15(Long story15_id, HttpServletRequest httpServletRequest)throws Exception;
    long countStory15ForSynopsis(Long fifteen_id, HttpServletRequest httpServletRequest);


}
