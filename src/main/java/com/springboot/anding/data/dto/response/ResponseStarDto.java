package com.springboot.anding.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStarDto {
    private Long starId;
    private Long fiveId;
    private Long tenId;
    private Long fifteenId;
    private Long uid;

}
