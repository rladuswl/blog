<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp"%>

<div class="container">

    <form action="/auth/loginProc" method="post">

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <button id="btn-login" class="btn btn-primary">로그인</button>

    </form>
</div>

<!-- 자바스크립트 사용하지 않고 form에서 액션 타서 보낼거라 아래 문장 사용하지 않음-->
<!--<script src="/js/user.js"><</script>-->
<%@ include file="../layout/footer.jsp"%>