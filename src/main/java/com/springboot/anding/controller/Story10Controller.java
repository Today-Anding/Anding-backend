package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10Dto;
import com.springboot.anding.data.dto.response.ResponseStory10ListDto;
import com.springboot.anding.data.dto.response.ResponseStory5ListDto;
import com.springboot.anding.service.Story10Service;
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

    @GetMapping(value = "/getStory10/{ten_id}/{position}")
    public ResponseEntity<ResponseStory10Dto> getStory10(@PathVariable Long ten_id,
                                                       @PathVariable int position) throws Exception {
        ResponseStory10Dto selectedStory10Dto = story10Service.getStory10(ten_id,position);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory10Dto);
    }

    @GetMapping("/completed/{tenId}")
    public ResponseEntity<ResponseStory10ListDto> getCompleteStory10List(
            @PathVariable Long tenId) throws Exception{
        ResponseStory10ListDto selectedStory10DtoList = story10Service.getCompleteStory10List(tenId);
        return ResponseEntity.status(HttpStatus.OK).body(selectedStory10DtoList);

    }

    @GetMapping("/incomplete/{tenId}")
    public ResponseEntity<ResponseStory10ListDto> getIncompleteStory10List(
            @PathVariable Long tenId) throws Exception{
        ResponseStory10ListDto selectedStory10DtoList = story10Service.getImcompleteStory10List(tenId);
        return  ResponseEntity.status(HttpStatus.OK).body(selectedStory10DtoList);

    }
    @DeleteMapping("/deleteStory10")
    public void deleteStory10(@RequestParam(value = "story10_id",required = true) Long story10_id,
                             HttpServletRequest httpServletRequest) throws Exception {
        story10Service.deleteStory10(story10_id,httpServletRequest);
        LOGGER.info("[deleteStory10] 중편 스토리를 삭제를 완료하였습니다.  , story10_id: {}", story10_id);
    }

    @GetMapping("/countStory10/{ten_id}")
    public ResponseEntity<Map<String, Object>> countStory10ForSynopsis(@PathVariable Long ten_id,
                                                         HttpServletRequest servletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long count = story10Service.countStory10ForSynopsis(ten_id, servletRequest);;
            response.put("이어진 앤딩(글) 수", count);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "시놉시스를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
