package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestStarDto;
import com.springboot.anding.data.dto.response.ResponseStarDto;
import com.springboot.anding.data.dto.response.ResponseStarListDto;
import com.springboot.anding.data.entity.Star;
import com.springboot.anding.service.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/star")
@RequiredArgsConstructor
public class StarController {
    @Autowired
    private StarService starService;

    @PostMapping("/createStar/five")
    public ResponseEntity<String> createStarForStory5(@RequestBody RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception {
        boolean isStarAdded = starService.addStarForFive(requestStarDto, servletRequest);
        if (isStarAdded) {
            return ResponseEntity.status(HttpStatus.OK).body("단편앤딩 읽기목록 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("단편앤딩 읽기목록 삭제되었습니다.");
        }
    }

    @PostMapping("/createStar/ten")
    public ResponseEntity<String> createStarForStory10(@RequestBody RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception {
        boolean isStarAdded = starService.addStarForTen(requestStarDto, servletRequest);
        if (isStarAdded) {
            return ResponseEntity.status(HttpStatus.OK).body("중편앤딩 읽기목록 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("중편앤딩 읽기목록 삭제되었습니다.");
        }
    }

    @PostMapping("/createStar/fifteen")
    public ResponseEntity<String> createStarForStory15(@RequestBody RequestStarDto requestStarDto, HttpServletRequest servletRequest) throws Exception {
        boolean isStarAdded = starService.addStarForFifteen(requestStarDto, servletRequest);
        if (isStarAdded) {
            return ResponseEntity.status(HttpStatus.OK).body("장편앤딩 읽기목록 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("장편앤딩 읽기목록 삭제되었습니다.");
        }
    }

    @GetMapping("/getTop4RecentStar")
    public ResponseEntity<ResponseStarListDto> getTop4RecentStar(HttpServletRequest httpServletRequest){
        ResponseStarListDto top3List = starService.getTop4RecentStar(httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(top3List);
    }



}
