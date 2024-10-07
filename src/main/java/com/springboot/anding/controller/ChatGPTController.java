package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestChatGPT;
import com.springboot.anding.data.dto.response.ResponseChatGPT;
import com.springboot.anding.service.Story10Service;
import com.springboot.anding.service.Story15Service;
import com.springboot.anding.service.Story5Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@RestController
@RequiredArgsConstructor
public class ChatGPTController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;
    private final Story5Service story5Service;
    private final Story10Service story10Service;
    private final Story15Service story15Service;
    private final RestTemplate template;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatGPTController.class);

    @GetMapping("/api/v1/main/getGptApi")
    public String getGptApi(@RequestParam(name = "prompt") String prompt) {
        RequestChatGPT request = new RequestChatGPT(model, prompt);
        ResponseChatGPT response = template.postForObject(apiURL, request, ResponseChatGPT.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @PostMapping("/api/v1/main/story5Compare")
    public String compareAndReturnResult5(@RequestParam Long fiveId, @RequestParam String newContent) {
        // five_id로 새로운 Story5를 저장하기 전에 비교하고 연결 여부를 반환
        return story5Service.compareAndReturnResult5(fiveId, newContent);
    }
    @PostMapping("/api/v1/main/story10Compare")
    public String compareAndReturnResult10(@RequestParam Long tenId, @RequestParam String newContent) {
        // five_id로 새로운 Story5를 저장하기 전에 비교하고 연결 여부를 반환
        return story10Service.compareAndReturnResult10(tenId, newContent);
    }
    @PostMapping("/api/v1/main/story15Compare")
    public String compareAndReturnResult15(@RequestParam Long fifteenId, @RequestParam String newContent) {
        // five_id로 새로운 Story5를 저장하기 전에 비교하고 연결 여부를 반환
        return story15Service.compareAndReturnResult15(fifteenId, newContent);
    }
}
