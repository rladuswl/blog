package com.yj.blog.test;

import lombok.*;

//@Getter
//@Setter
// @Data // Getter + Setter
// @AllArgsConstructor // 모든 생성자
// @RequiredArgsConstructor // final 붙은 것들에 대한 생성자
@Data
@NoArgsConstructor // 빈 생성자
public class Member {

    // 객체지향에서는 변수에 다이렉트로 접근할 수 없도록 private
    // 변수는 메서드에 의해 변경이 되도록 설계되어야 함
    private int id;
    private String username;
    private String password;
    private String email;

    // @AllArgsConstructor 과 같은 역할
    @Builder // Member m = Member.builder().username("yeongon").password("1234").email("yeongon@gmail.com").build(); 처럼 사용 가능 (id 빼고 생성자 사용)
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
