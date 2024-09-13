package com.springboot.anding.data.dto.response.synopsis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResposneFifthteenDto {
    private Long fifthteen_id;
    private String title;
    private String description;
    private String content;
    private String thumbnail;
    private boolean is_finished;
}
