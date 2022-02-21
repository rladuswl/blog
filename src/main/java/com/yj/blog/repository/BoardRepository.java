package com.yj.blog.repository;

import com.yj.blog.model.Board;
import com.yj.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능
public interface BoardRepository extends JpaRepository<Board, Integer> {

}