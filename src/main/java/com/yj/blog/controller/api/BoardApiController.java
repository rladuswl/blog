package com.yj.blog.controller.api;

import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.dto.RelySaveRequestDto;
import com.yj.blog.dto.ResponseDto;
import com.yj.blog.model.Board;
import com.yj.blog.model.Reply;
import com.yj.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.글수정하기(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody RelySaveRequestDto relySaveRequestDto) {
//        boardService.댓글쓰기(principal.getUser(), boardId, reply);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);

        // 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
        boardService.댓글쓰기(relySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
