package com.yj.blog.service;

import com.yj.blog.model.User;
import com.yj.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // 전체가 성공해야 commit, 실패하면 rollback
    public void 회원가입(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true) // Select 할 때 트랜잭션 시작 -> 서비스 종료시에 트랜잭션 종료 까지 "정합성" 유지
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
