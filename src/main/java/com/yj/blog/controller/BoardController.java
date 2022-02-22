package com.yj.blog.controller;

import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.model.Board;
import com.yj.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 메인페이지 페이징 url : http://localhost:8000/?page=0
    @GetMapping({"", "/"})
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Board> pagingBoard = boardService.글목록(pageable);
//        List<Board> boards = pagingBoard.getContent(); // getContent() 하면 List 타입으로 받을 수 있음
//        model.addAttribute("boards", boards);

        model.addAttribute("boards", boardService.글목록(pageable)); // List 가 아닌 Page 타입으로..

        // /WEB-INF/views/index.jsp
        return "index"; // viewResolver 작동 (@Controller)
    }

    // 글 작성 폼
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    // 글 상세보기
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }
}
