package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestStory15Dto;
import com.springboot.anding.data.dto.request.RequestStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.service.Story15Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/story10")
public class Story15Controller {

    private final Story15Service story15Service;
    private final Logger LOGGER = LoggerFactory.getLogger(Story15Controller.class);

    @Autowired
    public Story15Controller(Story15Service story15Service) {
        this.story15Service = story15Service;
    }

    @PostMapping("/createStory15")
    public ResponseEntity<ResponseStory15Dto> createStory15(@RequestBody RequestStory15Dto requestStory15Dto,
                                                           HttpServletRequest httpServletRequest)throws Exception{
        ResponseStory15Dto savedStory15Dto = story15Service.saveStory15(requestStory15Dto,httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(savedStory15Dto);
    }

    @GetMapping(value = "/getStory15/{fifteen_id}/{story15_id}")
    public ResponseEntity<ResponseStory15Dto> getStory15(@PathVariable Long fifteen_id,
                                                       @PathVariable Long story15_id) throws Exception {
        ResponseStory15Dto selectedStory15Dto = story15Service.getStory15(fifteen_id,story15_id);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory15Dto);
    }

    @GetMapping("/countStory15/{fifteen_id}")
    public ResponseEntity<String> countStory15ForSynopsis(@PathVariable Long fifteen_id,
                                                         HttpServletRequest servletRequest) {
        try {
            Long count = story15Service.countStory15ForSynopsis(fifteen_id, servletRequest);
            return ResponseEntity.status(HttpStatus.OK).body("이어진 앤딩(글) 수: " + count); //시놉시스가 1개로 default
        } catch (IllegalArgumentException e) {
            // 시놉시스가 존재하지 않을 때의 메시지 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("시놉시스를 찾을 수 없습니다.");
        }
    }
}
