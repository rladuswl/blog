package com.yj.blog.controller.api;

import com.yj.blog.dto.ResponseDto;
import com.yj.blog.model.RoleType;
import com.yj.blog.model.User;
import com.yj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

// Data만 리턴
@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private HttpSession session; // 스프링 컨테이너가 session을 빈으로 등록해서 가지고 있어서 DI 가능, DI 안 하려면 매개변수에 HttpSession session 넣으면 됨

   // 회원가입
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 통신 성공 : 200, 실패 : 500 // 자바 오브젝트를 JSON으로 변환해서 리턴 (Jackson)
    }

    // 전통적인 로그인 방식 (시큐리티 이용 X)
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user) { // DI 대신 매개변수에 HttpSession session 넣어도 됨
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user); // principal : 접근주체
//
//        if (principal != null) { // null이 아니면 세션 만들기
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }
}
