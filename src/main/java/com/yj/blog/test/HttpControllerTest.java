package com.yj.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML 파일) : @Controller
// 사용자가 요청 -> 응답(Data) : @RestController
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("yeongon").password("1234").email("yeongon@gmail.com").build();
//        Member m = new Member(1, "yeongon", "1234", "yeongon@gmail.com");
        System.out.println(TAG + "getter : " + m.getId());
        m.setId(5000);
        System.out.println(TAG + "setter : " + m.getId());
        return "lombok test 완료";
    }

    // 인터넷 브라우저 요청은 무조건 get 요청 밖에 할 수 없다. -> key=value 형태라서 @RequestBody 어노테이션 필요하지 않다.
    // http://localhost:8080/http/get (select)
    // http://localhost:8080/http/get?id=1&username=yeongon 처럼 [물음표+쿼리스트링]을 통해 데이터 보낼 수 있음, 매개변수는 (@RequestParam int id, @RequestParam String username) 로 받아야 함
    // 하지만 매개변수에 @RequestParam 를 일일이 적는 것이 아니라 Member 객체를 적어주면 알아서 매핑됨, 매핑 - MessageConverter (스프링부트) 가 함
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    // http://localhost:8080/http/post (insert)
    // 데이터를 주소에 담아 보내는 쿼리스트링이 아니라, Body 에 담아 보냄
    // Body 데이터는 @RequestBody 를 사용 - 객체로 매핑해서 받을 수 있음
    // raw 데이터 - text/plain, application/json 등
    // text/plain 으로 날릴 때는 일반 문자열이여서 매개변수가 (@RequestBody String text) 이지만,
    // application/json 으로 날릴 때는 자동으로 파싱하여 객체에 넣어주기 때문에 매개변수를 (@RequestBody Member m) 으로 받아서 매핑 가능, 매핑 - MessageConverter (스프링부트) 가 함
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) {
        return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    // http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    // http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
