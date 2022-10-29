package com.springproject.weathersharecommunity.Controller;

import com.springproject.weathersharecommunity.http.DefaultRes;
import com.springproject.weathersharecommunity.http.StatusCode;
import com.springproject.weathersharecommunity.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;


    @GetMapping("/test")
    public String test(){
        return "Hello World";
    }

    @GetMapping("/test/user/all")
    public ResponseEntity allMember(){
        return new ResponseEntity(DefaultRes.defaultRes(StatusCode.OK, "전체 멤버 조회", testService.testAllMember()), HttpStatus.OK);
    }
}
