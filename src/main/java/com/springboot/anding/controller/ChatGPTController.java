package com.springboot.anding.controller;

import com.springboot.anding.data.dto.request.RequestChatGPT;
import com.springboot.anding.data.dto.response.ResponseChatGPT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@RequiredArgsConstructor
public class ChatGPTController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate template;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatGPTController.class);

    @GetMapping("/api/v1/main/getGptApi")
    public String getGptApi(@RequestParam(name = "prompt") String prompt) {
        RequestChatGPT request = new RequestChatGPT(model, prompt);
        ResponseChatGPT response = template.postForObject(apiURL, request, ResponseChatGPT.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
