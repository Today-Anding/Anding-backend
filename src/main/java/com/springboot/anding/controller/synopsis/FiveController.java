package com.springboot.anding.controller.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFiveListDto;
import com.springboot.anding.service.synopsis.FiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/five")
public class FiveController {
    private final FiveService fiveService;
    private final Logger LOGGER = LoggerFactory.getLogger(FiveController.class);


    @Autowired
    public FiveController(FiveService fiveService) {
        this.fiveService = fiveService;
    }

    @PostMapping("/createFive")
    public ResponseEntity<ResponseFiveDto> createFive(@RequestBody RequestFiveDto requestFiveDto,
                                                      HttpServletRequest httpServletRequest)throws Exception{
        ResponseFiveDto savedFiveDto = fiveService.saveFive(requestFiveDto,httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(savedFiveDto);
    }

    @GetMapping(value = "/getFive/{five_id}")
    public ResponseEntity<ResponseFiveDto> getFive(@PathVariable Long five_id) throws Exception {
        ResponseFiveDto selectedFiveDto = fiveService.getFive(five_id);
        return ResponseEntity.status(HttpStatus.OK).body(selectedFiveDto);
    }

    @DeleteMapping("/deleteFive")
    public void deleteFive(@RequestParam(value = "five_id",required = true) Long five_id,
                           HttpServletRequest httpServletRequest) throws Exception {
        fiveService.deleteFive(five_id,httpServletRequest);
        LOGGER.info("[deleteFive] 단편 시놉시스를 삭제를 완료하였습니다.  , five_id: {}", five_id);
    }

    @GetMapping("/getFiveList")
    public ResponseEntity<ResponseFiveListDto> getFiveList(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        // 서비스 호출하여 데이터 조회
        ResponseFiveListDto selectedFiveDtoList = fiveService.getFiveList(httpServletRequest, httpServletResponse);

        return ResponseEntity.status(HttpStatus.OK).body(selectedFiveDtoList);
    }



}
