package com.springboot.anding.data.dto.sign;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class SignUpDto {
    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String account;
    private String password;
    private String name;
    private String gender;
    private String phone;
    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;



}
