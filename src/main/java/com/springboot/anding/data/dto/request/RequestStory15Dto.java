package com.springboot.anding.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStory15Dto {
    private Long fifteenId; //연결되는 시놉시스 id
    private String content;
}
