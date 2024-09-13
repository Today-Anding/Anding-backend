package com.springboot.anding.data.dto.request.synopsis;

import com.springboot.anding.data.entity.synopsis.Five;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFiveDto {
    private String title;
    private String description;
    private String content;
    private String thumbnail;

}
