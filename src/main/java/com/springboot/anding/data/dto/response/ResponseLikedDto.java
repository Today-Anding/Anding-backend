package com.springboot.anding.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLikedDto {
    private Long likedId;
    private Long fiveId;
    private Long tenId;
    private Long fifteenId;
    private Long story5Id;
    private Long story10Id;
    private Long story15Id;
    private String title;
    private String writer;
    private Long uid;
}
