package com.yj.blog.service;

import com.yj.blog.model.Board;
import com.yj.blog.model.User;
import com.yj.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC)
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional // 전체가 성공해야 commit, 실패하면 rollback
    public void 글쓰기(Board board, User user) { // title, content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }
}
