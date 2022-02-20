package com.yj.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 안된 사용자들이 출입할 수 있는 경로 : /auth/** (auth의 하위) 허용, 주소가 그냥 / 이면 index.jsp로 가는데 이 곳도 허용, static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm") // 회원가입하러 들어가는데 인증 될 필요 없으므로 /auth/
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }
}
