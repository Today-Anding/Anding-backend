package com.springboot.anding.controller.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestFifteenDto;
import com.springboot.anding.data.dto.request.synopsis.RequestTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseFifteenListDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenListDto;
import com.springboot.anding.service.synopsis.FifteenService;
import com.springboot.anding.service.synopsis.TenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/fifteen")
public class FifteenController {
    private final FifteenService fifteenService;
    private final Logger LOGGER = LoggerFactory.getLogger(TenController.class);

    @Autowired
    public FifteenController(FifteenService fifteenService) {
        this.fifteenService = fifteenService;
    }

    @PostMapping("/createFifteen")
    public ResponseEntity<ResponseFifteenDto> createFifteen(@RequestBody RequestFifteenDto requestFifteenDto,
                                                        HttpServletRequest httpServletRequest)throws Exception{
        ResponseFifteenDto savedFifteenDto = fifteenService.saveFifteen(requestFifteenDto,httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(savedFifteenDto);
    }

    @GetMapping(value = "/getFifteen/{fifteen_id}")
    public ResponseEntity<ResponseFifteenDto> getFifteen(@PathVariable Long fifteen_id) throws Exception {
        ResponseFifteenDto selectedFifteenDto = fifteenService.getFifteen(fifteen_id);
        return ResponseEntity.status(HttpStatus.OK).body(selectedFifteenDto);
    }

    @DeleteMapping("/deleteFifteen")
    public void deleteFifteen(@RequestParam(value = "fifteen_id",required = true) Long fifteen_id,
                          HttpServletRequest httpServletRequest) throws Exception {
        fifteenService.deleteFifteen(fifteen_id,httpServletRequest);
        LOGGER.info("[deleteFifteen] 장편 시놉시스 삭제를 완료하였습니다.  , fifteen_id: {}", fifteen_id);
    }

    @GetMapping("/getFifteenList")
    public ResponseEntity<ResponseFifteenListDto> getFifteenList(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        // 서비스 호출하여 데이터 조회
        ResponseFifteenListDto selectedFifteenDtoList = fifteenService.getFifteenList(httpServletRequest, httpServletResponse);

        return ResponseEntity.status(HttpStatus.OK).body(selectedFifteenDtoList);
    }

}
