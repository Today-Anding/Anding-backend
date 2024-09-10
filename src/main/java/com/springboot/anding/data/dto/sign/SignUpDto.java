package com.springboot.anding.data.dto.sign;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpDto {
    private String account;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String nickname;



}
