package com.yj.blog.config.auth;

import com.yj.blog.model.User;
import com.yj.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, username과 password 변수 2개를 가로챔
    // password 처리는 스프링이 알아서 함
    // 나는 username이 해당 DB에 있는지만 확인해서 리턴해주면 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User principal = userRepository.findByUsername(username) // Optional 타입이기 때문에 .orElseThrow
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
                });

        return new PrincipalDetail(principal); // 이때 시큐리티 세션에 유저정보 저장됨
    }
}
