package com.springboot.anding.service;

import com.springboot.anding.data.dto.request.RequestStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory5ListDto;

import javax.servlet.http.HttpServletRequest;

public interface Story5Service {
    ResponseStory5Dto saveStory5(RequestStory5Dto requestStory5Dto,
                                 HttpServletRequest httpServletRequest);
    ResponseStory5Dto getStory5(Long fiveId, int position)throws Exception;
    ResponseStory5ListDto getCompleteStory5List(Long fiveId) throws Exception;
    ResponseStory5ListDto getImcompleteStory5List(Long fiveId) throws Exception;

    void deleteStory5(Long story5_id,HttpServletRequest httpServletRequest)throws Exception;
    long countStory5ForSynopsis(Long five_id, HttpServletRequest httpServletRequest);


}
