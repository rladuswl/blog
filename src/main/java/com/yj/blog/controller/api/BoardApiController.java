package com.yj.blog.controller.api;

import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.dto.ResponseDto;
import com.yj.blog.model.Board;
import com.yj.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Data만 리턴
@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) { // 컨트롤러에서 로그인 된 세션을 찾는 방식 : @AuthenticationPrincipal PrincipalDetail principal
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
