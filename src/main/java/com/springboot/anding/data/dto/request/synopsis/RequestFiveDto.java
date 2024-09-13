package com.springboot.anding.data.dto.request.synopsis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFiveDto {
    private String title;
    private String description;
    private String content;
    private String thumbnail;
}
