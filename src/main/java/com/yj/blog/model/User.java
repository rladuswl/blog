package com.yj.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// JPA는 ORM -> Java(다른 언어 포함) Object를 테이블로 매핑해주는 기술
// 개발자가 Object를 만들면 JPA가 테이블을 만들어줌

@Data // getter setter
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Builder // 빌더 패턴
@Entity // User 클래스가 MySQL에 자동으로 테이블이 생성된다.
// @DynamicInsert // insert 할 때 null인 필드 제외
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. (오라클이든 MySQL이든.. 등등), 내 DB는 MySQL이니까 auto_increment가 됨
    private int id; // 오라클-시퀀스, MySQL-auto_increment

    @Column(nullable = false, length = 30, unique = true) // null이 될 수 없고, 30자까지 밖에 안된다.
    private String username; // 아이디

    @Column(nullable = false, length = 100) // null이 될 수 없고, 100자까지 밖에 안된다. (100자 넣는 이유 : 해쉬(비밀번호 암호화)할 것을 대비해서 넉넉하게 만들기)
    private String password;

    @Column(nullable = false, length = 50) // null이 될 수 없고, 50자까지 밖에 안된다.
    private String email;

    // @ColumnDefault("'user'") // default값 만들기, 문자라는 것 알려주기 위해 ''로 감싸고 ""로 다시 감싸기
    @Enumerated(EnumType.STRING) // DB에는 RoleType이라는게 없어서 이걸 붙여줘야함
    private RoleType role; // Enum을 쓰면 데이터의 도메인(범위)을 만들어 줄 수 있다. (도메인 : admin, user, manager ...) -> 하지만 우선 String으로.. -> 추후 Enum으로 설정

    @CreationTimestamp // 시간이 자동으로 입력, 내가 직접 시간을 넣으려면 Timestamp.valueOf(LocalDateTime.now())
    private Timestamp createDate;
}
