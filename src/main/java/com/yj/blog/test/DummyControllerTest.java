package com.yj.blog.test;


import com.yj.blog.model.RoleType;
import com.yj.blog.model.User;
import com.yj.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController // html 파일이 아니라 data를 리턴해주는 controller
public class DummyControllerTest {

    @Autowired // 스프링 컨테이너에 UserRepository 타입으로 메모리에 떠있으면 userRepository에 넣어줌 (의존성 주입)
    private UserRepository userRepository;

    // {id} 주소로 파라미터를 전달 받을 수 있음
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {

        // findById의 리턴 값은 Optional임
        // 그 이유는 -> user/4를 찾는데 만약 데이터베이스에서 못 찾아오게 되면 user가 null이 된다.
        // 그러면 return null; 이 된다. 이렇게 되면 프로그램에 문제가 생겨서 Optional로 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 리턴하라는 것이다.
        // Optional의 함수 : [1] .get() - null이 리턴되지 않는다고 장담(?)할 때 사용, [2] .orElseGet() - 익명 객체 생성하기

        // .orElseGet() -> 만약 id 값이 DB에 존재한다면 .orElseGet ~ return User(); 까지의 코드는 해당하지 않을 것이다. 빈 객체를 user에 넣으면 null은 아니기 때문에 사용한다.
//        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//            @Override
//            public User get() {
//                return new User();
//            }
//        });

        // 아래의 방법 [3]에서 람다식으로 표현할 수도 있다.
        // orElseThrow에 어떤 타입(Supplier)이 들어가야하는지 등.. 신경쓰지 않아도 돼서 편리하다. -> 익명처리
//        User user = userRepository.findById(id).orElseThrow(()->{
//            return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//        });

        // 다른 방법 !! [3] @throws IllegalArgumentException if {@literal id} is {@literal null}.
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        // 요청 : 웹 브라우저, 응답 : 스프링 서버
        // 데이터를 리턴하는 RestController가 웹 브라우저에게 user 객체(= 자바 오브젝트)를 리턴한다.
        // 웹 브라우저는 자바 오브젝트 이해 못한다. -> 변환 (웹 브라우저가 이해할 수 있는 데이터) : json
        // 스프링 부트의 MessageCoverter가 응답(스프링 서버)할 때 Jackson 라이브러리를 호출해서 user 객체를 json으로 변환하여 웹 브라우저에게 던짐.
        return user;
    }


    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 요청
    // 1. x-www-form-uriencoded 로 전송되는 데이터는 key=value 형태 (약속된 규칙) -> 스프링이 함수의 파라미터로 파싱해서 넣어줌
    // 2. 파라미터를 User 객체로 받기
    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("id:"+user.getId());
        System.out.println("username:"+user.getUsername());
        System.out.println("password:"+user.getPassword());
        System.out.println("email:"+user.getEmail());
        System.out.println("role:"+user.getRole());
        System.out.println("createDate:"+user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
