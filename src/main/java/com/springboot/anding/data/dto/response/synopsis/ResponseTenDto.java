package com.springboot.anding.data.dto.response.synopsis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTenDto {
    private Long ten_id;
    private String title;
    private String description;
    private String content;
    private String thumbnail;
    private boolean is_finished;
}
