package com.springboot.anding.controller;

import com.springboot.anding.data.dto.sign.SignInResultDto;
import com.springboot.anding.data.dto.sign.SignUpDto;
import com.springboot.anding.data.dto.sign.SignUpResultDto;
import com.springboot.anding.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SignController {

    private Logger logger = LoggerFactory.getLogger(SignController.class);
    private SignService signService;

    @Autowired
    public SignController(SignService signService){

        this.signService = signService;
    }

//    @PostMapping("/sign-up")
//    public SignUpResultDto SignUp(@RequestBody SignUpDto signUpDto, String roles){
//        logger.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", signUpDto.getAccount(),
//                signUpDto.getPassword(),roles);
//        SignUpResultDto signUpResultDto = signService.SignUp(signUpDto,roles);
//
//        return signUpResultDto;
//    }
@PostMapping("/sign-up")
public ResponseEntity<Map<String, Object>> signUp(@RequestBody SignUpDto signUpDto, String roles) {
    logger.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}",
            signUpDto.getAccount(), signUpDto.getPassword(), signUpDto.getName(), roles);

    Map<String, Object> response = new HashMap<>();

    // 로그인 아이디 중복 체크
    if (signService.checkLoginIdDuplicate(signUpDto.getAccount())) {
        logger.warn("[signUp] 중복된 로그인 아이디: {}", signUpDto.getAccount());
        response.put("success", false);
        response.put("message", "로그인 아이디가 중복됩니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // 닉네임 중복 체크
    if (signService.checkNicknameDuplicate(signUpDto.getNickname())) {
        logger.warn("[signUp] 중복된 닉네임: {}", signUpDto.getNickname());
        response.put("success", false);
        response.put("message", "닉네임이 중복됩니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // 회원가입 처리
    SignUpResultDto signUpResultDto = signService.SignUp(signUpDto, roles);
    response.put("data", signUpResultDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
}


    @PostMapping("/sign-in")
    public SignInResultDto SignIn(@RequestParam String account, String password) {
        logger.info("[sign-in] 로그인을 시도하고 있습니다. id : {}, password : *****", account);
        SignInResultDto signInResultDto = signService.SignIn(account, password);
        if(signInResultDto.getCode() == 0){
            logger.info("[sign-in] 정상적으로 로그인이 되었습니다. id: {}, token : {}",account,signInResultDto.getToken());
            signInResultDto.getToken();
        }
        return signInResultDto;
    }
    @GetMapping(value = "/exception")
    public void exceptionTest()throws  RuntimeException{
        throw new RuntimeException("접근이 금지 되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        logger.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
