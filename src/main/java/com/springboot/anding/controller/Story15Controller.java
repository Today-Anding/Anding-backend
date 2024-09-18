package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestStory15Dto;
import com.springboot.anding.data.dto.response.ResponseStory15Dto;
import com.springboot.anding.service.Story15Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping(value = "/getStory15/{fifteen_id}/{position}")
    public ResponseEntity<ResponseStory15Dto> getStory15(@PathVariable Long fifteen_id,
                                                       @PathVariable int position) throws Exception {
        ResponseStory15Dto selectedStory15Dto = story15Service.getStory15(fifteen_id,position);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory15Dto);
    }
    @DeleteMapping("/deleteStory15")
    public void deleteStory15(@RequestParam(value = "story15_id",required = true) Long story15_id,
                             HttpServletRequest httpServletRequest) throws Exception {
        story15Service.deleteStory15(story15_id,httpServletRequest);
        LOGGER.info("[deleteStory15] 장편 스토리를 삭제를 완료하였습니다.  , story15_id: {}", story15_id);
    }

    @GetMapping("/countStory15/{fifteen_id}")
    public ResponseEntity<Map<String, Object>> countStory15ForSynopsis(@PathVariable Long fifteen_id,
                                                                       HttpServletRequest servletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long count = story15Service.countStory15ForSynopsis(fifteen_id, servletRequest);

            response.put("이어진 앤딩(글) 수", count);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "시놉시스를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
