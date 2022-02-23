package com.yj.blog.controller.api;

import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.dto.ResponseDto;
import com.yj.blog.model.RoleType;
import com.yj.blog.model.User;
import com.yj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) { // id, password, email
        userService.회원수정(user);
        // 이 타이밍이
        // (Service)트랜잭션이 종료되고 DB에 값이 변경이 된 상태
        // 하지만 세션 값은 변경되지 않은 상태 -> 따라서 직접 세션 값 변경해야 함

        // 세션 등록 (DB가 변경된 후의 정보를 가져와야 하므로 Service가 끝난 후(=트랜잭션 끝난 후)에 이루어져야 한다. 따라서 Controller에서 진행
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 통신 성공 : 200, 실패 : 500 // 자바 오브젝트를 JSON으로 변환해서 리턴 (Jackson)
    }

}
