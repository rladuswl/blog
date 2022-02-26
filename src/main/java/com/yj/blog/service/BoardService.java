package com.yj.blog.service;

import com.yj.blog.dto.RelySaveRequestDto;
import com.yj.blog.model.Board;
import com.yj.blog.model.Reply;
import com.yj.blog.model.User;
import com.yj.blog.repository.BoardRepository;
import com.yj.blog.repository.ReplyRepository;
import com.yj.blog.repository.UserRepository;
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

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id) // 영속화 (영속성 컨텍스트에 넣는 것)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다." + id);
                });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수 종료시(Service가 종료될 때) 트랜잭션 종료 -> 영속화 되어있는 board 데이터가 달라졌기 때문에 더티체킹 일어남 -> 변경 감지 -> DB에 자동 flush
    }

    @Transactional // 전체가 성공해야 commit, 실패하면 rollback
    public void 댓글쓰기(RelySaveRequestDto relySaveRequestDto) {
//        User user = userRepository.findById(relySaveRequestDto.getUserId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다. id : " + relySaveRequestDto.getUserId());
//                }); // 영속화 완료
//
//        Board board = boardRepository.findById(relySaveRequestDto.getBoardId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다. id : " + relySaveRequestDto.getBoardId());
//                }); // 영속화 완료
//
//        Reply reply = Reply.builder()
//                .user(user)
//                .board(board)
//                .content(relySaveRequestDto.getContent())
//                .build();
//
//        replyRepository.save(reply);

        // 네이티브 쿼리 사용하기
        int result = replyRepository.mSave(relySaveRequestDto.getUserId(), relySaveRequestDto.getBoardId(), relySaveRequestDto.getContent());
        System.out.println("BoardService : " + result);
    }

}
