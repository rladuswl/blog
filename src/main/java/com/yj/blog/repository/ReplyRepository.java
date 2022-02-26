package com.yj.blog.repository;

import com.yj.blog.dto.RelySaveRequestDto;
import com.yj.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying // Modifying 쿼리는 int만 리턴 가능
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1,?2,?3,now())", nativeQuery = true) // true 라고 하면 내가 작성한 쿼리 호출, 네이밍 쿼리가 아니기 때문에 작성
    int mSave(int userId, int boardId, String content); // insert, update, delete -> 업데이트된 행의 개수를 리턴해준다.(int형)
}
