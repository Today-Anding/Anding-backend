package com.springboot.anding.controller.synopsis;

import com.springboot.anding.data.dto.request.synopsis.RequestTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenDto;
import com.springboot.anding.data.dto.response.synopsis.ResponseTenListDto;
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
@RequestMapping("/api/v1/ten")
public class TenController {

    private final TenService tenService;
    private final Logger LOGGER = LoggerFactory.getLogger(TenController.class);


    @Autowired
    public TenController(TenService tenService) {
        this.tenService = tenService;
    }

    @PostMapping("/createTen")
    public ResponseEntity<ResponseTenDto> createTen(@RequestBody RequestTenDto requestTenDto,
                                                     HttpServletRequest httpServletRequest)throws Exception{
        ResponseTenDto savedTenDto = tenService.saveTen(requestTenDto,httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(savedTenDto);
    }

    @GetMapping(value = "/getTen/{ten_id}")
    public ResponseEntity<ResponseTenDto> getTen(@PathVariable Long ten_id) throws Exception {
        ResponseTenDto selectedTenDto = tenService.getTen(ten_id);
        return ResponseEntity.status(HttpStatus.OK).body(selectedTenDto);
    }

    @DeleteMapping("/deleteTen")
    public void deleteTen(@RequestParam(value = "ten_id",required = true) Long ten_id,
                           HttpServletRequest httpServletRequest) throws Exception {
        tenService.deleteTen(ten_id,httpServletRequest);
        LOGGER.info("[deleteTen] 중편 시놉시스 삭제를 완료하였습니다.  , ten_id: {}", ten_id);
    }

    @GetMapping("/getTenList")
    public ResponseEntity<ResponseTenListDto> getTenList(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        // 서비스 호출하여 데이터 조회
        ResponseTenListDto selectedTenDtoList = tenService.getTenList(httpServletRequest, httpServletResponse);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTenDtoList);
    }

}
