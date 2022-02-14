package com.yj.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 데이터 리턴(Json 등)이 아닌 "파일" 리턴 (<-> @RestController 랑은 다름)
public class TempControllerTest {

    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("tempHome()");
        // 파일리턴 기본경로 : src/main/resources/static 인데
        // return "home.html"; 라고 하면
        // 파일리턴 풀경로 : src/main/resources/statichome.html 이 되는 것이다.

        // 따라서 파일리턴 기본 경로에 리턴명을 /home.html 로 하면
        // 파일리턴 풀경로 : src/main/resources/static/home.html
        // + 추가) tempJsp() 메소드를 위해 jsp 설정해놔서 이제 정적파일(html, img 등)은 동작할 수 없음. JSP file [/WEB-INF/views/home.html.jsp] not found 이렇게 에러 나옴
        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        // prefix: /WEB-INF/views/
        // suffix: .jsp
        // return "/test.jsp";
        // 풀경로는 /WEB-INF/views//test.jsp.jsp 이 된다.

        // 하지만 return "test"; 로 한다면
        // 풀경로는 /WEB-INF/views/test.jsp 이 되어 찾아지게 된다.
        return "test";
    }
}
