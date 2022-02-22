package com.yj.blog.service;

import com.yj.blog.model.Board;
import com.yj.blog.model.User;
import com.yj.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Transactional(readOnly = true) // select만 하기 때문
    public Page<Board> 글목록(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다." + id);
                });
    }

    @Transactional
    public void 글삭제하기(int id) {
        System.out.println("글삭제하기 : "+id);
        boardRepository.deleteById(id);
    }

}
