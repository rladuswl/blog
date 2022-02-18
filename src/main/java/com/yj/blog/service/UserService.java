package com.yj.blog.service;

import com.yj.blog.model.User;
import com.yj.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // 전체가 성공해야 commit, 실패하면 rollback
    public void 회원가입(User user) {
        userRepository.save(user);
    }
}
