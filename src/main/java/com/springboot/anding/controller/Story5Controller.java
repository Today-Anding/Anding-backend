package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory5Dto;
import com.springboot.anding.data.dto.response.ResponseStory5ListDto;
import com.springboot.anding.service.Story5Service;
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

    @GetMapping(value = "/getStory5/{fiveId}/{position}")
    public ResponseEntity<ResponseStory5Dto> getStory5(@PathVariable Long fiveId,
                                                       @PathVariable int position) throws Exception {
        ResponseStory5Dto selectedStory5Dto = story5Service.getStory5(fiveId,position);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory5Dto);
    }

    @GetMapping("/completed/{fiveId}")
    public ResponseEntity<ResponseStory5ListDto> getCompleteStory5List(
            @PathVariable Long fiveId) throws Exception{
        ResponseStory5ListDto selectedStory5DtoList = story5Service.getCompleteStory5List(fiveId);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory5DtoList);

    }

    @GetMapping("/incomplete/{fiveId}")
    public ResponseEntity<ResponseStory5ListDto> getIncompleteStory5List(
            @PathVariable Long fiveId) throws Exception{
        ResponseStory5ListDto selectedStory5DtoList = story5Service.getImcompleteStory5List(fiveId);
        return  ResponseEntity.status(HttpStatus.OK).body(selectedStory5DtoList);

    }

    @DeleteMapping("/deleteStory5")
    public void deleteStory5(@RequestParam(value = "story5_id",required = true) Long story5_id,
                           HttpServletRequest httpServletRequest) throws Exception {
        story5Service.deleteStory5(story5_id,httpServletRequest);
        LOGGER.info("[deleteStory5] 단편 스토리를 삭제를 완료하였습니다.  , story5_id: {}", story5_id);
    }

    @GetMapping("/countStory5/{five_id}")
    public ResponseEntity<Map<String, Object>> countStory5ForSynopsis(@PathVariable Long five_id,
                                                                      HttpServletRequest servletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long count = story5Service.countStory5ForSynopsis(five_id, servletRequest);;
            response.put("이어진 앤딩(글) 수", count);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "시놉시스를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
