package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10Dto;
import com.springboot.anding.service.Story10Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/story10")
public class Story10Controller {

    private final Story10Service story10Service;
    private final Logger LOGGER = LoggerFactory.getLogger(Story10Controller.class);

    @Autowired
    public Story10Controller(Story10Service story10Service) {
        this.story10Service = story10Service;
    }

    @PostMapping("/createStory10")
    public ResponseEntity<ResponseStory10Dto> createStory10(@RequestBody RequestStory10Dto requestStory10Dto,
                                                            HttpServletRequest httpServletRequest)throws Exception{
        ResponseStory10Dto savedStory10Dto = story10Service.saveStory10(requestStory10Dto,httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(savedStory10Dto);
    }

    @GetMapping(value = "/getStory10/{ten_id}/{story10_id}")
    public ResponseEntity<ResponseStory10Dto> getStory10(@PathVariable Long ten_id,
                                                       @PathVariable Long story10_id) throws Exception {
        ResponseStory10Dto selectedStory10Dto = story10Service.getStory10(ten_id,story10_id);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory10Dto);
    }

    @GetMapping("/countStory10/{ten_id}")
    public ResponseEntity<String> countStory10ForSynopsis(@PathVariable Long ten_id,
                                                         HttpServletRequest servletRequest) {
        try {
            Long count = story10Service.countStory10ForSynopsis(ten_id, servletRequest);
            return ResponseEntity.status(HttpStatus.OK).body("이어진 앤딩(글) 수: " + count); //시놉시스가 1개로 default
        } catch (IllegalArgumentException e) {
            // 시놉시스가 존재하지 않을 때의 메시지 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("시놉시스를 찾을 수 없습니다.");
        }
    }
}
