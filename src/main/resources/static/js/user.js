let index = {
    init: function () {
        $("#btn-save").on("click", ()=>{ // function(){} 대신 ()=>{} 를 쓴 이유 : this를 바인딩하기 위해서
            this.save();
        });
        //$("#btn-login").on("click", ()=>{ // function(){} 대신 ()=>{} 를 쓴 이유 : this를 바인딩하기 위해서
        //    this.login();
        //});
    },

    save: function () {
        // alert('user의 save함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        // console.log(data); data는 자바스크립트 오브젝트
        // console.log(JSON.stringify(data)); JSON 문자열

        // ajax 호출시 default가 비동기 호출 -> 순서 상관없음
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바(스크립트?) 오브젝트로 변환
        $.ajax({
            // 회원가입 수행 요청
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
            dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 String(문자열), 만약 생긴게 json이라면 javascript 오브젝트로 변경
        }).done(function (resp) {
            // 결과가 정상이면 done 실행
            alert("회원가입이 완료되었습니다.");
            //console.log(resp);
            location.href = "/";
        }).fail(function (error) {
            // 실패하면 fail 실행
            alert("회원가입이 실패하였습니다.");
            alert(JSON.stringify(error));
        });
    },

    /*
    login: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
            dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 String(문자열), 만약 생긴게 json이라면 javascript 오브젝트로 변경
        }).done(function (resp) {
            // 결과가 정상이면 done 실행
            alert("로그인이 완료되었습니다.");
            //console.log(resp);
            location.href = "/";
        }).fail(function (error) {
            // 실패하면 fail 실행
            alert("로그인이 실패하였습니다.");
            alert(JSON.stringify(error));
        });
    }
     */
}


index.init();

