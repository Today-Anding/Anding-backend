package com.springboot.anding.data.dto.request;

import java.util.List;

import com.springboot.anding.data.dto.Message;
import lombok.Data;

@Data
public class RequestChatGPT {
    private String model;
    private List<Message> messages;
    public RequestChatGPT(String model, String prompt) {
        this.model = model;
        this.messages = List.of(new Message("user", prompt));
    }
}
