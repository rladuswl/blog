package com.yj.blog.handler;

import com.yj.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception 발생시 이 클래스로 오게 만들기
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)// IllegalArgumentException 발생시 해당 메소드, 모든 예외를 여기서 처리하고 싶다면 모든 예외의 부모인 Exception 으로 바꾸기
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//        return "<h1>" + e.getMessage() + "</h1>";
    }
}
