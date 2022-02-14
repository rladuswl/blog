package com.yj.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data // getter setter
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Builder // 빌더 패턴
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 사용함 (MySQL)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 - 쓴 글이 디자인 되는데 <html> 태그가 섞여서 디자인이 됨 -> 글자수 커짐 -> 대용량

    @ColumnDefault("0") // String이 아니여서 ''로 안 감싸도 됨
    private int count; // 조회수

    // 데이터베이스는 오브젝트(객체)를 저장할 수 없어서 FK를 사용한다. BUT 자바는 객체를 저장할 수 있다. -> 두 개의 충돌!
    // 따라서 자바가 데이터베이스에 맞춰 FK(int)로 저장 -> private int userId;

    // 하지만 JPA를 사용하면! ORM에서는 객체를 그대로 저장할 수 있다. -> FK로 찾는게 아니라 User 객체 바로 넣음
    @ManyToOne // 연관관계 Many = Board, One = User
    @JoinColumn(name = "userId") // 데이터베이스에다가 만들 필드 이름
    private User user; // 글 작성한 사람의 id, 자동으로 FK로 만들어지는 것

    @CreationTimestamp // 자동으로 현재시간이 들어감
    private Timestamp createDate;
}
