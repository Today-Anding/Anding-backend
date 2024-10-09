package com.springboot.anding.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLikedDto {
    private Long LikedId;
    private Long fiveId;
    private Long tenId;
    private Long fifteenId;
    private String title;
    private Long uid;
}
