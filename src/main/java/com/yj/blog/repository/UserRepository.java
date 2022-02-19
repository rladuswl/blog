package com.yj.blog.repository;

import com.yj.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA Naming 쿼리 전략
    User findByUsernameAndPassword(String username, String password); // SELECT * FROM user WHERE username = ?1 AND password = ?2

//    위에 findByUsernameAndPassword랑 같은 방식
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}
