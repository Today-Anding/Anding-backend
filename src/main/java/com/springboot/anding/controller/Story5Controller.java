package com.springboot.anding.controller;

import com.springboot.anding.controller.synopsis.TenController;
import com.springboot.anding.data.dto.request.RequestStory5Dto;
import com.springboot.anding.data.dto.request.synopsis.RequestFiveDto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;
import com.springboot.anding.service.Story5Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/story5")
public class Story5Controller {

    private final Story5Service story5Service;
    private final Logger LOGGER = LoggerFactory.getLogger(Story5Controller.class);

    @Autowired
    public Story5Controller(Story5Service story5Service) {
        this.story5Service = story5Service;
    }

    @PostMapping("/createStory5")
    public ResponseEntity<ResponseStory5Dto> createStory5(@RequestBody RequestStory5Dto requestStory5Dto,
                                                          HttpServletRequest httpServletRequest)throws Exception{
        ResponseStory5Dto savedStory5Dto = story5Service.saveStory5(requestStory5Dto,httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(savedStory5Dto);
    }

    @GetMapping(value = "/getStory5/{five_id}/{story5_id}")
    public ResponseEntity<ResponseStory5Dto> getStory5(@PathVariable Long five_id,
                                                       @PathVariable Long story5_id) throws Exception {
        ResponseStory5Dto selectedStory5Dto = story5Service.getStory5(five_id,story5_id);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory5Dto);
    }

    @GetMapping("/countStory5/{five_id}")
    public ResponseEntity<String> countStory5ForSynopsis(@PathVariable Long five_id,
                                                       HttpServletRequest servletRequest) {
        try {
            Long count = story5Service.countStory5ForSynopsis(five_id, servletRequest);
            return ResponseEntity.status(HttpStatus.OK).body("이어진 앤딩(글) 수: " + count); //시놉시스가 1개로 default
        } catch (IllegalArgumentException e) {
            // 시놉시스가 존재하지 않을 때의 메시지 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("시놉시스를 찾을 수 없습니다.");
        }
    }
}
