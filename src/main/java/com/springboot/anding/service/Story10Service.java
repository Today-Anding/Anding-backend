package com.springboot.anding.service;

import com.springboot.anding.data.dto.request.RequestStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10ListDto;
import com.springboot.anding.data.dto.response.ResponseStory5ListDto;

import javax.servlet.http.HttpServletRequest;

public interface Story10Service {
    ResponseStory10Dto saveStory10(RequestStory10Dto requestStory10Dto,
                                   HttpServletRequest httpServletRequest);
    ResponseStory10Dto getStory10(Long ten_id, int position)throws Exception;
    ResponseStory10ListDto getCompleteStory10List(Long tenId) throws Exception;
    ResponseStory10ListDto getImcompleteStory10List(Long tenId) throws Exception;

    void deleteStory10(Long story10_id,HttpServletRequest httpServletRequest)throws Exception;
    long countStory10ForSynopsis(Long ten_id, HttpServletRequest httpServletRequest);


}
