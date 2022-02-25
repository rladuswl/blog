package com.yj.blog.test;

import com.yj.blog.model.Board;
import com.yj.blog.model.Reply;
import com.yj.blog.repository.BoardRepository;
import com.yj.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // reply를 board를 통해 호출 테스트
    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id) {

        return boardRepository.findById(id).get(); // jackson 라이브러리 작동 -> 오브젝트를 json으로 리턴 -> model의 getter를 호출
    }

    // reply를 다이렉트로 호출 테스트트
    @GetMapping("/test/reply")
    public List<Reply> getReply() {

        return replyRepository.findAll();
    }
}
