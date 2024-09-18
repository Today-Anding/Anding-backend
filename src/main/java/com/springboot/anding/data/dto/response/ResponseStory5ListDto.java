package com.springboot.anding.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStory5ListDto {
    private List<ResponseStory5Dto> items;

}
