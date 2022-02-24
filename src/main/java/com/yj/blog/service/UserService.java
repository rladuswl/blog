package com.yj.blog.service;

import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.model.RoleType;
import com.yj.blog.model.User;
import com.yj.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional // 전체가 성공해야 commit, 실패하면 rollback
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 원본 (예 : 1234)
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    // 아래는 전통적인 방식 (시큐리티 사용 X) 로그인 방식에서 사용
//    @Transactional(readOnly = true) // Select 할 때 트랜잭션 시작 -> 서비스 종료시에 트랜잭션 종료 까지 "정합성" 유지
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }

    @Transactional // 전체가 성공해야 commit, 실패하면 rollback
    public void 회원수정(User user) {
        // 수정시에는 JPA 영속성 컨텍스트에 User 객체를 영속화 시키고, 영속화된 User 객체를 수정하면 된다.
        // select 하여 DB에서 가져와 영속화를 한다.
        // 영속화된 객체를 변경하면 자동으로 변경감지되어 (더티체킹) DB에 update 된다. (flush)
        User persistance = userRepository.findById(user.getId()) // select 하여 영속화
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패, id : "+user.getId());
                });

        // Validate 체크 (카카오 로그인 사용자는 password, email을 변경할 수 없다.)
        if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);

            persistance.setPassword(encPassword); // 영속화 되어 있는 객체를 변경
            persistance.setEmail(user.getEmail());
        }

        // 회원 수정 함수 종료 시(Service 종료 시) -> 트랜잭션 종료 -> commit(영구적)이 자동으로 된다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹 되어 자동으로 DB에 update문을 날려준다.
    }

    @Transactional(readOnly = true) // select만 하기 때문에 readonly
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{ // .orElseGet : 회원을 찾았는데 없으면, 빈 객체 리턴
            return new User();
        });
        return user;
    }
}
