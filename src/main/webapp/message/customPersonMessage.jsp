<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>customPersonMessage.jsp</title>
    <script>
    'use strict';
    //JavaScript 변수는 server실행일 경우 el표기법으로 사용가능하나, client실행일 경우는 순차가 안맞아서 사용안됨
    let msg = '${msg}';
    
    <!--/customPerson/회원관리 - 회원가입,회원탈퇴신청(30일임시삭제),회원탈퇴처리(회원DB삭제),회원로그인,회원로그아웃,개별회원조회,운영자회원용 전체회원목록조회-->
    if (msg == 'customPersonLoginNo') msg = "로그인 실패";
    else if (msg == 'customPersonLoginOk') msg = "${sCustomName}님 로그인됬습니다";
    else if (msg == 'customPersonIdFindoutNo') msg = "아이디 찾기 실패";
    else if (msg == 'customPersonIdFindoutOk') msg = "회원님의 아이디를 찾았습니다";
    else if (msg == 'customPersonPwdChangeNo') msg = "비밀번호 수정 실패";
    else if (msg == 'customPersonPwdChangeOk') msg = "회원님의 비밀번호를 수정했습니다";
    else if (msg == 'customPersonLogoutOk') msg = "${customName}님 로그아웃됬습니다";
    else if (msg == 'customPersonEntryOk') msg = "회원으로 가입됬습니다.";
    else if (msg == 'customPersonEntryNo') msg = "회원 가입 실패";
    else if (msg == 'customPersonUpdateOk') msg = "회원정보가 수정됬습니다.";
    else if (msg == 'customPersonUpdateNo') msg = "회원정보 수정 실패";
    else if (msg == 'customPersonLevelUpdateOk') msg = "회원레벨이 수정됬습니다";
    else if (msg == 'customPersonLevelUpdateNo') msg = "회원레벨 수정 실패";
    else if (msg == 'customPersonDeletePractOk') msg = "회원에서 탈퇴됬습니다";
    else if (msg == 'customPersonDeletePractNo') msg = "회원 탈퇴 실패";
    else if (msg == 'customPersonDeleteOk') msg = "회원DB에서 회원을 삭제했습니다";
    else if (msg == 'customPersonDeleteNo') msg = "회원DB에서 회원 삭제 실패";
    
    <!--/admin/관리자 - 관리자로그인(인증), 관리자로그아웃 -->
    else if (msg == 'adminOk') msg = "관리자 인증됬습니다";
    else if (msg == 'adminNo') msg = "관리자 인증 실패";
    else if (msg == 'adminLogoutOk') msg = "관리자 로그아웃됬습니다";

    <!--/pds/자료실 - 화일업로드, 화일다운로드, 화일삭제-->
    else if (msg == 'pdsInputOk') msg = "파일이 등록되었습니다";
    else if (msg == 'pdsInputNo') msg = "파일 등록 실패";
    <!--/schedule/일정관리 - CRUD -->
    else if (msg == 'scheduleUpdateOk') msg = "스케쥴이 수정됬습니다";
    else if (msg == 'scheduleUpdateNo') msg = "스케쥴 수정 실패";
    <!--/emailbox/이메일박스 - CRUD -->
    else if (msg == 'emailboxGabegeOk') msg = "이메일을 휴지통으로 보냈습니다";
    else if (msg == 'emailboxGabegeNo') msg = "휴지통 전송 실패";
    else if (msg == 'emailboxInputOk') msg = "이메일을 보냈습니다";
    else if (msg == 'emailboxInputNo') msg = "이메일 보내기 실패";
    
    alert(msg);
    if ('${url}' != '') location.href = '${url}';
    </script>
</head>
<body>
</body>
</html>