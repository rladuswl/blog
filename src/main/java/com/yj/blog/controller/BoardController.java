package com.yj.blog.controller;

import com.yj.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"", "/"})
    public String index() { // 컨트롤러에서 로그인 된 세션을 찾는 방식 : @AuthenticationPrincipal PrincipalDetail principal
        // /WEB-INF/views/index.jsp
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
