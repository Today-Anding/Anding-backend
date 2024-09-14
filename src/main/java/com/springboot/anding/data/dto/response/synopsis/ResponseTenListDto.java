package com.springboot.anding.data.dto.response.synopsis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTenListDto {
    private List<ResponseTenDto> items;
}
