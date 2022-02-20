package com.yj.blog.repository;

import com.yj.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {

    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);
}




// 아래는 전통적인 로그인 방식에서 사용 -> 스프링 시큐리티로 바꿔서 더이상 사용 X

// 1) JPA Naming 쿼리 전략
//    User findByUsernameAndPassword(String username, String password); // SELECT * FROM user WHERE username = ?1 AND password = ?2

// 2) 위에 findByUsernameAndPassword랑 같은 방식
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
