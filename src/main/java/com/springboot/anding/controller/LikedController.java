package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestLikedDto;
import com.springboot.anding.data.dto.request.RequestStarDto;
import com.springboot.anding.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/liked")
@RequiredArgsConstructor
public class LikedController {
    @Autowired
    private final LikedService likedService;
    @PostMapping("/createLikedForAnding/five")
    public ResponseEntity<String> createLikedForFive(@RequestBody RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        boolean isLikedAdded = likedService.addLikeForFive(requestLikedDto, servletRequest);
        if (isLikedAdded) {
            return ResponseEntity.status(HttpStatus.OK).body("단편앤딩 좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("단편앤딩 좋아요가 삭제되었습니다.");
        }
    }

    @PostMapping("/createLikedForAnding/ten")
    public ResponseEntity<String> createLikedForTen(@RequestBody RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        boolean isLikedAdded = likedService.addLikeForTen(requestLikedDto, servletRequest);
        if (isLikedAdded) {
            return ResponseEntity.status(HttpStatus.OK).body("중편앤딩 좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("중편앤딩 좋아요가 삭제되었습니다.");
        }
    }

    @PostMapping("/createLikedForAnding/fifteen")
    public ResponseEntity<String> createLikedForFifteen(@RequestBody RequestLikedDto requestLikedDto, HttpServletRequest servletRequest) throws Exception {
        boolean isLikedAdded = likedService.addLikeForFifteen(requestLikedDto, servletRequest);
        if (isLikedAdded) {
            return ResponseEntity.status(HttpStatus.OK).body("장편앤딩 좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("장편앤딩 좋아요가 삭제되었습니다.");
        }
    }

    @PostMapping("/createLikedForWriter/story5")
    public ResponseEntity<String> addLikeForWriter5(@RequestBody RequestLikedDto requestLikedDto, HttpServletRequest request) throws Exception {
        boolean likedAdded = likedService.addLikeForWriterForStory5(requestLikedDto, request);
        if (likedAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("단편작가 좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("단편작가 좋아요가 삭제되었습니다.");
        }
    }

    @PostMapping("/createLikedForWriter/story10")
    public ResponseEntity<String> addLikeForWriter10(@RequestBody RequestLikedDto requestLikedDto, HttpServletRequest request) throws Exception {
        boolean likedAdded = likedService.addLikeForWriterForStory10(requestLikedDto, request);
        if (likedAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("중편작가 좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("단편작가 좋아요가 삭제되었습니다.");
        }
    }

    @PostMapping("/createLikedForWriter/story15")
    public ResponseEntity<String> addLikeForWriter15(@RequestBody RequestLikedDto requestLikedDto, HttpServletRequest request) throws Exception {
        boolean likedAdded = likedService.addLikeForWriterForStory15(requestLikedDto, request);
        if (likedAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("장편작가 좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("장편작가 좋아요가 삭제되었습니다.");
        }
    }


}
