package com.yj.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yj.blog.config.auth.PrincipalDetail;
import com.yj.blog.model.KakaoProfile;
import com.yj.blog.model.OAuthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// 인증이 안된 사용자들이 출입할 수 있는 경로 : /auth/** (auth의 하위) 허용, 주소가 그냥 / 이면 index.jsp로 가는데 이 곳도 허용, static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm") // 회원가입하러 들어가는데 인증 될 필요 없으므로 /auth/
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody
    String kakaoCallback(String code) { // @ResponseBody : Data를 리턴해주는 컨트롤러 함수

        // 카카오 API 서버에게 POST 방식으로 key=value 데이터를 요청
        // 요청 방법 -> 여러가지 라이브러리  : HttpsURLConnection, Retrofit2(주로 안드로이드), OkHttp, RestTemplate
        RestTemplate rt = new RestTemplate();

        // HttpHeader 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터라는 것을 알려주는 부분

        // HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d5526e3b2a4169a3d9f2b7a6f9a12cdf");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 객체에 담기 -> 만든 이유 : 아래의 exchange 함수에 HttpEntity를 넣어야 해서..
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers); // body 데이터와 headers 값을 가지고 있는 Entity

        // 카카오에게 Http 요청하기 (POST 방식) -> response라는 변수에 응답을 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // oauthToken에 응답 데이터 담기
        // json 데이터를 자바에서 처리하기 위해 자바 객체로 바꿔야 한다.
        // 객체(현재는 OAuthToken)에 담을 때 사용할 수 있는 라이브러리 : Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

        // ------------------------------------------------------------------------------------

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 객체 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터라는 것을 알려주는 부분

        // HttpHeader를 객체에 담기 -> 만든 이유 : 아래의 exchange 함수에 HttpEntity를 넣어야 해서..
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2); // headers 값을 가지고 있는 Entity

        // 카카오에게 Http 요청하기 (POST 방식) -> response2라는 변수에 응답을 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        // kakaoProfile에 응답 데이터 담기
        // json 데이터를 자바에서 처리하기 위해 자바 객체로 바꿔야 한다.
        // 객체(현재는 OAuthToken)에 담을 때 사용할 수 있는 라이브러리 : Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper2 = new ObjectMapper();

        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : id, username, password, email, role, createDate
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

//        return "카카오 인증 완료 : 코드값 : " + code;
//        return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답 : " + response.getBody();
        return "액세스 토큰으로 회원정보 요청 : 회원정보 요청에 대한 응답 : " + response2.getBody();
    }
}
