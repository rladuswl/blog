package com.yj.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Data // getter setter
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Builder // 빌더 패턴
@Entity
public class Reply {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. auto_increment 사용함 (MySQL)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // 연관관계 Many = Reply, One = Board
    @JoinColumn(name = "boardId")
    private Board board; // 댓글 달 게시글

    @ManyToOne // 연관관계 Many = Reply, One = User
    @JoinColumn(name = "userId")
    private User user; // 댓글 단 사람

    @CreationTimestamp
    private Timestamp createDate;

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", board=" + board +
                ", user=" + user +
                ", createDate=" + createDate +
                '}';
    }
}
