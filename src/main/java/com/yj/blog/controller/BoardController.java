package com.yj.blog.controller;

import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.글목록());

        // /WEB-INF/views/index.jsp
        return "index"; // viewResolver 작동 (@Controller)
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
