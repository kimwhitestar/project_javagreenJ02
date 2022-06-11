<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<c:set var="security" value="<%= new common.SecurityUtil() %>" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>customCompUpdate.jsp</title>
	<%@ include file="/include/bs4.jsp" %>
	<!-- daum웹사이트에서 제공하는 script open 예제소스 -->
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${ctxPath}/js/post.js"></script>
	<style>
	</style>
	<script>
	'use strict';
	
	//사업자등록번호 중복 체크 
	function companyNoCheck() {
		let url = '${ctxPath}/customCompCompanyNoCheck.cmbr';
		let childWin = window.open(url,"companyNoCheckWin","width=580px,height=300px");

		// Jquery방식으로, 자식창에서 윈도우 로딩시에 Document를 메모리로 모두 올리고 작업준비한다.
		childWin.onload = function() {
			//자식창에 사업자등록번호 값 셋팅
			childWin.document.getElementById("companyNo").value = $("#companyNo").val();
		}
	}
	
	//이메일 중복 체크
	function emailCheck() {
		let url = '${ctxPath}/customCompEmailCheck.cmbr';
		let childWin = window.open(url,"emailCheckWin","width=580px,height=300px");

		// Jquery방식으로, 자식창에서 윈도우 로딩시에 Document를 메모리로 모두 올리고 작업준비한다.
		childWin.onload = function() {
			//자식창 이메일 값 셋팅
			childWin.document.getElementById("email1").value = $("#email1").val();
			childWin.document.getElementById("email2").value = $("#email2").val();
			childWin.document.getElementById("txtEmail2").value = $("#txtEmail2").val();
		}
	}
	
	//회원정보수정폼 체크
	function checkUpdateForm() {
		if ( editForm(regexCheck()) ) {
			updateForm.submit();
		}
	}
	
	//DB에 저장될 각각의 필드길이 체크
 	function regexCheck() {
		const regexId = /^[a-zA-Z]+[0-9_+-.]*[a-zA-Z_+-.]*[0-9]([a-zA-Z0-9_+-.]*)$/g; //아이디체크(영문자1자리이상, 숫자나 특수기호 조합 2~20자리)
    const regexPassword = /([a-zA-Z][0-9][@#$%&!?^~*+-_.]|[0-9][a-zA-Z][@#$%&!?^~*+-_.]|[@#$%&!?^~*+-_.][a-zA-Z][0-9]|[@#$%&!?^~*+-_.][0-9][a-zA-Z])/g;//비밀번호체크(영문자,숫자,특수기호 @#$%&!?^~*+-_. 조합 3~20자리)
  	const regexCustomName = /[가-힣a-zA-Z]{3,10}([0-9]*)/g; //기업명체크(한글or영문에 필요하면 숫자포함 조합 3~20자리)
    const regexCustomNameShort = /[가-힣a-zA-Z]{3,10}([0-9]*)/g; //기업명(단축명칭)체크(한글or영문에 필요하면 숫자포함 조합 3~20자리)
		const regexCompanyNo = /[0-9]{3}-[0-9]{2}-[0-9]{5}/g; //사업자등록번호체크(숫자3자리-숫자2자리-숫자5자리)
		const regexTxtOffice = /[가-힣a-zA-Z0-9]{1,100}/g; //사무실명체크(한글 또는 영문 또는 숫자 1~100자리)
    const regexEmail1 = /^[a-zA-Z]{2}[0-9_+-.]*[a-zA-Z]([a-zA-Z0-9_+-.]*)$/g;//이메일체크(영문자으로 시작하여 특수기호 _+-. 또는 숫자 또는 문자 조합 3~25자리)
		const regexTxtEmail2 = /[a-zA-Z][0-9]*[_+-.][a-zA-Z0-9]([_+-.][a-zA-Z]|[a-zA-Z])/g;//도메인체크(domain deep 2이상, 영문자와 특수기호 _+-. 또는 숫자 또는 문자 조합 3~25자리)
    const regexTel2 = /\d{3,4}$/g; //전화번호2체크(숫자 3~4자리)
		const regexTel3 = /\d{4}$/g; //전화번호3체크(숫자 4자리)
	  const regexHp2 = /\d{4}$/g; //휴대폰번호2체크(숫자 4자리)
		const regexHp3 = /\d{4}$/g; //휴대폰번호3체크(숫자 4자리)
    const regexDetailAddress = /[a-zA-Z0-9가-힣#-. ]{1,50}/g; //상세주소체크(한글or영문에 필요하면 숫자 또는 특수문자( .-#) 1~50자리)
		const regexMemo = /[a-zA-Z0-9가-힣]([@#$%^&!?]|[~()<>_*+-=]|[,.:;\/]|[ ]*)/gm; //메모 체크(숫자,문자,특수문자체크(~!?@#$%^&*()<>_+=-,.:;/ ) 조합 500자리)
		let regexFlag = true;
		
 		//회원아이디 정규식 체크
		if ( ! $("#id").val().match(regexId) ) {
 			$("#id").addClass("is-invalid");
 			$("#idInvalid").addClass("is-invalid");
 			$("#idInvalid").text("영문자로 시작하여 숫자1자리 이상 포함하는 영문,숫자,특수기호(_+-.)의 조합 2~20자리로 입력하세요");
 			$("#id").focus();
 			regexFlag = false;
 		} else {
 			$("#id").addClass("is-valid");
 			$("#idInvalid").addClass("is-valid");
 			$("#idInvalid").text("");
 			$("#password").focus();
 		}
		//비밀번호 정규식 체크
		if ( ! $("#password").val().match(regexPassword) ) {
			$("#password").addClass("is-invalid");
			$("#passwordInvalid").addClass("is-invalid");
			$("#passwordInvalid").text("영문자, 숫자, 특수기호(~!?@#$%^&*_+-.) 조합 3~20자리로 입력하세요");
 			$("#password").focus();
 			regexFlag = false;
		} else {
			$("#password").addClass("is-valid");
			$("#passwordInvalid").addClass("is-valid");
 			$("#passwordInvalid").text("");
			$("#customName").focus();
		}
		//기업명 정규식 체크
		if ( ! $("#customName").val().match(regexCustomName) ) {
			$("#customName").addClass("is-invalid");
			$("#customNameInvalid").addClass("is-invalid");
			$("#customNameInvalid").text("한글 또는 영문 3자리 이상으로 필요하면 숫자 조합 3~20자리로 입력하세요");
			$("#customName").focus();
 			regexFlag = false;
		} else {
			$("#customName").addClass("is-valid");
			$("#customNameInvalid").addClass("is-valid");
			$("#customNameInvalid").text("");
			$("#customNameShort").focus();
		}
		//기업명(단축명칭) 정규식 체크
		if ( ! $("#customNameShort").val().match(regexCustomNameShort) ) {
			$("#customNameShort").addClass("is-invalid");
			$("#customNameShortInvalid").addClass("is-invalid");
			$("#customNameShortInvalid").text("한글 또는 영문 3자리 이상으로 필요하면 숫자 조합 3~20자리로 입력하세요");
			$("#customNameShort").focus();
 			regexFlag = false;
		} else {
			$("#customNameShort").addClass("is-valid");
			$("#customNameShortInvalid").addClass("is-valid");
			$("#customNameShortInvalid").text("");
			$("#email1").focus();
		}
		//사업자등록번호 정규식 체크
		if ( ! $("#companyNo").val().match(regexCompanyNo) ) {
			$("#companyNo").addClass("is-invalid");
			$("#companyNoInvalid").addClass("is-invalid");
			$("#companyNoInvalid").text("숫자-숫자-숫자로 입력하세요");
			$("#companyNo").focus();
 			regexFlag = false;
		} else {
			$("#companyNo").addClass("is-valid");
			$("#companyNoInvalid").addClass("is-valid");
			$("#companyNoInvalid").text("");
			$("#customKindCode").focus();
		}
		//사무실명 정규식 체크
		if ( ! $("#txtOffice").val().match(regexTxtOffice) ) {
			$("#txtOffice").addClass("is-invalid");
			$("#txtOfficeInvalid").addClass("is-invalid");
			$("#txtOfficeInvalid").text("한글 또는 영문 또는 숫자 1~100자리로 입력하세요");
			$("#txtOffice").focus();
 			regexFlag = false;
		} else {
			$("#txtOffice").addClass("is-valid");
			$("#txtOfficeInvalid").addClass("is-valid");
			$("#txtOfficeInvalid").text("");
			$("#postcode").focus();
		}
		//우편번호 공란 체크
		if ( '' == $('#addressGroup input[name="postcode"]').val()
			&& '' != $('#addressGroup input[name="detailAddress"]').val() ) {
			$('#addressGroup input[name="detailAddress"]').addClass("is-invalid");
			$("#detailAddressInvalid").addClass("is-invalid");
			$("#detailAddressInvalid").text("주소는 우편번호찾기로 검색 후 입력하세요");
			$('#addressGroup input[name="detailAddress"]').focus();
 			regexFlag = false;
		} else {
			$('#addressGroup input[name="detailAddress"]').addClass("is-valid");
			$("#detailAddressInvalid").addClass("is-valid");
			$("#detailAddressInvalid").text("");
			$("#btnPostCode").focus();
		}
		//상세주소 정규식 체크
 		if ( '' != $('#addressGroup input[name="postcode"]').val() 
 			&& '' != $('#addressGroup input[name="detailAddress"]').val() ) {
			if ( ! $('#addressGroup input[name="detailAddress"]').val().match(regexDetailAddress) ) {
				$('#addressGroup input[name="detailAddress"]').addClass("is-invalid");
				$("#detailAddressInvalid").addClass("is-invalid");
				$("#detailAddressInvalid").text("한글 또는 영문에 필요하면 숫자, 특수문자( .-#) 포함하여 1~50자리로 입력하세요");
				$('#addressGroup input[name="detailAddress"]').focus();
	 			regexFlag = false;
			} else {
				$('#addressGroup input[name="detailAddress"]').addClass("is-valid");
				$("#detailAddressInvalid").addClass("is-valid");
				$("#detailAddressInvalid").text("");
				$("#email1").focus();
			}
		}
		//이메일 정규식 체크
		if ( ! $("#email1").val().match(regexEmail1) ) {
			$("#email1").addClass("is-invalid");
			$("#email1Invalid").addClass("is-invalid");
			$("#email1Invalid").text("영문자 3자리 이상으로 필요하면 특수기호(_+-.), 숫자, 영문 조합 3~25자리로 입력하세요");
			$("#email1").focus();
			regexFlag = false;
		} else {
			$("#email1").addClass("is-valid");
			$("#email1Invalid").addClass("is-valid");
			$("#email1Invalid").text("");
			$("#email2").focus();
		}
		//이메일 도메인 정규식 체크
		let options = updateForm.email2.options;
  	if(options[0].selected == true) {//도메인 셀렉트박스에서 '-직접입력-' 선택의 경우
  		if (updateForm.txtEmail2.value == '' ) {//도메인 직접입력 텍스트박스가 공란의 경우
				$("#txtEmail2").addClass("is-invalid");
				$("#txtEmail2Invalid").addClass("is-invalid");
				$("#txtEmail2Invalid").text("해당하는 이메일 도메인명이 없으면 직접입력하세요");
				$("#txtEmail2").focus();
				regexFlag = false;
  		} else {//도메인 직접입력 텍스트박스에 입력한 경우
				if ( ! $("#txtEmail2").val().match(regexTxtEmail2) ) {//도메인값 REGEX 유효성 체크
					$("#txtEmail2").addClass("is-invalid");
					$("#txtEmail2Invalid").addClass("is-invalid");
					$("#txtEmail2Invalid").text("도메인명은 영문자와 특수기호(_+-.), 숫자, 영문 조합 3~25자리까지 입력가능합니다");
					$("#txtEmail2").focus();
		 			regexFlag = false;
				} else {
					$("#txtEmail2").addClass("is-valid");
					$("#txtEmail2Invalid").addClass("is-valid");
					$("#txtEmail2Invalid").text("");
					$("#tel1").focus();
				}
  		}
  	}	else {//도메인 셀렉트박스에서 선택한 경우
			$("#txtEmail2").addClass("is-valid");
			$("#txtEmail2Invalid").addClass("is-valid");
			$("#txtEmail2Invalid").text("");
			$("#tel1").focus();
  	}
		//전화번호 정규식 체크
		if ('' != $("#tel3").val()) {
			if ( ! $("#tel3").val().match(regexTel3) ) {
				$("#tel3").addClass("is-invalid");
				$("#tel3Invalid").addClass("is-invalid");
				$("#tel3Invalid").text("숫자 4자리로 입력하세요");
				$("#tel3").focus();
	 			regexFlag = false;
			} else {
 				$("#tel3").addClass("is-valid");
				$("#tel3Invalid").addClass("is-valid");
				$("#tel3Invalid").text('');
			}
		} 
		//전화번호 정규식 체크
		if ('' != $("#tel2").val()) {
			if ( ! $("#tel2").val().match(regexTel2) ) {
				$("#tel2").addClass("is-invalid");
				$("#tel2Invalid").addClass("is-invalid");
				$("#tel2Invalid").text("숫자 3자리~4자리로 입력하세요");
				$("#tel2").focus();
	 			regexFlag = false;
			} else {
				$("#tel2").addClass("is-valid");
				$("#tel2Invalid").addClass("is-valid");
				$("#tel2Invalid").text('');
			}
		}
		//휴대폰번호 정규식 체크
		if ('' != $("#hp3").val()) {
			if ( ! $("#hp3").val().match(regexHp3) ) {
				$("#hp3").addClass("is-invalid");
				$("#hp3Invalid").addClass("is-invalid");
				$("#hp3Invalid").text("숫자 4자리로 입력하세요");
				$("#hp3").focus();
	 			regexFlag = false;
			} else {
 				$("#hp3").addClass("is-valid");
				$("#hp3Invalid").addClass("is-valid");
				$("#hp3Invalid").text('');
			}
		} 
		//휴대폰번호 정규식 체크
		if ('' != $("#hp2").val()) {
			if ( ! $("#hp2").val().match(regexHp2) ) {
				$("#hp2").addClass("is-invalid");
				$("#hp2Invalid").addClass("is-invalid");
				$("#hp2Invalid").text("숫자 4자리로 입력하세요");
				$("#hp2").focus();
	 			regexFlag = false;
			} else {
				$("#hp2").addClass("is-valid");
				$("#hp2Invalid").addClass("is-valid");
				$("#hp2Invalid").text('');
			}
		}		
		//메모 정규식 체크
 		if ('' != $("#memo").val()) {
			if ( ! $("#memo").val().match(regexMemo) ) {
				$("#memo").addClass("is-invalid");
				$("#memoInvalid").addClass("is-invalid");
				$("#memoInvalid").text("문자, 숫자, 특수문자(~!?@#$%^&*()<>_+=-,.:;/ )의 조합 500자 이하로 입력하세요");
				$("#memo").focus();
	 			regexFlag = false;
			} else {
				$("#memo").addClass("is-valid");
				$("#memoInvalid").addClass("is-valid");
				$("#memoInvalid").text("");
				$("#customImgFileName").focus();
			}
		}

		return regexFlag;
	}
	
	//회원정보수정폼 파라미터 편집후 서버로 요청하기
 	function editForm(flag) {
		if (! flag) return false;
		
		//비밀번호 암호화
		setShaPwd($("#password").val());
		
		//사무실 편집
		if ('기타' == $("#office").val() && '' != $("#txtOffice").val()) {
			$("#txtOffice").val('기타:' + $("#txtOffice").val());
		}
		
		//이메일 편집
		if ('-' == $('#emailGroup select[name="email2"] option:selected').val()) {
			$("#email").val($("#email1").val() + '@' + $('#txtEmail2').val());
		} else {
			$("#email").val($("#email1").val() + '@' + $('#emailGroup select[name="email2"] option:selected').val());
		}


		//전화번호 편집
		let tel2 = $('#tel2').val();
		let tel3 = $('#tel3').val();
		$("#telNo").val(
			$('#telGroup select[name="tel1"] option:selected').val() 
			+ '-' + tel2 
			+ '-' + tel3);
		
		//휴대폰번호 편집
		let hp2 = $('#hp2').val();
		let hp3 = $('#hp3').val();
		if (''!=hp2 && ''!=hp3) {//필수항목이 아니므로, 편집전 공백체크
			$("#hpNo").val(
				$('#hpGroup select[name="hp1"] option:selected').val() 
				+ '-' + hp2 
				+ '-' + hp3);
		}
		
		//사진upload 체크
		if ('' == $("#customImgFileName").val().trim()) {
			$("#photo").val('noimage.jpg');
		} else {
			let fName = $("#customImgFileName").val();
			$("#photo").val(fName);
			let maxSize = 1024 * 1024 * 2;//업로드 회원사진 최대용량 = 2MB
			let fileSize = $("#customImgFileName")[0].files[0].size;
			if (maxSize < fileSize) {
				alert('업로드 파일의 크기는 2MByte를 초과할 수 없습니다.');
				$("#customImgFileName").focus();
				return false;
			}
		}
		
		return true;
	}
	
	//비밀번호 암호화
	function setShaPwd(password) {
			$("#shaPwd").val('${security.encryptSHA256(password)}');
	}
	
 	function changeSel(selObj, txtObj)	{
    	let options = selObj.options;
    	if(options[0].selected == true && txtObj.value == '' ) {
    		txtObj.focus();
    	} else {
    		options[0].selected = false;
    		txtObj.value = '';
    	}
 	}
 	
 	function changeTxt(selObj)	{
    	let options = selObj.options;
    	for (let i=0; i<options.length; i++) {
    		options[i].selected = false;
    	}
    	options[0].selected = true; 
 	}
 	
</script>
</head>
<body class="jumbotron">
<div class="container" style="padding:30px" >
  <form name="updateForm" method="post" action="${ctxPath}/customCompUpdateOk.cmbr" class="was-validated" >
<%--   <form name="updateForm" method="post" action="${ctxPath}/customCompUpdateOk.cmbr" class="was-validated" enctype="Multipart/form-data">
 --%>    
 		<h2 class="text-center">기 업 고 객 회 원 정 보 수 정</h2><br>
    <h6 class="text-center"><font color="blue">회원정보를 수정하려면 회원비밀번호 입력하세요.</font></h6><br>
    <div class="form-group">
			<label for="id">아이디 : ${sLoginId}</label>
    </div>
    <div class="form-group">
			<label for="password">비밀번호 확인 :</label>
			<input type="password" class="form-control" id="password" name="password" maxlength=20 required />
			<div id="passwordInvalid" class="invalid-feedback">비밀번호는 필수 입력사항입니다.</div>
			<input type="hidden" class="form-control" name="shaPwd" id="shaPwd" />
    </div>
    <div class="form-group">
			<label for="customName">기업명 :</label>
			<input type="text" class="form-control" id="customName" name="customName" value="${vo.customNm}" maxlength=20 required />
			<div id="customNameInvalid" class="invalid-feedback">기업명은 필수 입력사항입니다.</div>
    </div>
    <div class="form-group">
			<label for="customNameShort">기업명(단축명칭) : &nbsp; &nbsp;</label>
			<input type="text" class="form-control" id="customNameShort" name="customNameShort" value="${vo.customNmShort}" maxlength=20 required />
			<div id="customNameShortInvalid" class="invalid-feedback">기업명(단축명칭)은 필수 입력사항입니다.</div>
    </div>
    <div class="form-group">
    	<label for="estblDate">창립일 :</label>
			<input type="date" id="estblDate" name="estblDate" value="${vo.estblDate}" class="form-control"/>
    </div>
    <div class="form-group" id="companyNoGroup">
    	<label for="companyNo">사업자등록번호 : &nbsp; &nbsp;<input type="button" value="사업자등록번호 중복체크" class="btn btn-info" onclick="companyNoCheck()"/></label>
			<input type="text" class="form-control" id="companyNo" name="companyNo" value="${vo.companyNo}" maxlength=20 required/>
			<div id="companyNoInvalid" class="invalid-feedback">사업자등록번호는 필수 입력사항입니다.</div>
    </div>
    <div class="form-group">
			<label for="customKindCode">기업구분코드 :</label>
			<select id="customKindCode" name="customKindCode" class="custom-select">
				<c:forEach var="kindVo" items="${customKindVo}" >
					<option value="${kindVo.customKindCd}" ${ kindVo.customKindCd == vo.customKindCd ? 'selected' : '' } >${kindVo.customKindNm}</option>
				</c:forEach>
				<option value="1" ${ '1' == vo.customKindCd ? 'selected' : '' } >일반법인업체</option>
			</select>
		</div>
    <div class="form-group" id="officeGroup">
			<label for="office">사무실명 : </label>
			<div class="input-group ">
					<select class="form-control" id="office" name="office" onchange='changeSel(this,document.getElementById("txtOffice"))'>
						<option value="기타"   ${ '기타' == vo.office ? 'selected' : '' }>기타</option>
						<option value="물류과" ${ '물류과' == vo.office ? 'selected' : '' }>물류과</option>
						<option value="Ware Housing" ${ 'Ware Housing' == vo.office ? 'selected' : '' }>Ware Housing</option>
						<option value="입고과" ${ '입고과' == vo.office ? 'selected' : '' }>입고과</option>
						<option value="출고과" ${ '출고과' == vo.office ? 'selected' : '' }>출고과</option>
						<option value="운수과/운송과" ${ '운수과/운송과' == vo.office ? 'selected' : '' }>운수과/운송과</option>
						<option value="비서실" ${ '비서실' == vo.office ? 'selected' : '' }>비서실</option>
						<option value="기획실" ${ '기획실' == vo.office ? 'selected' : '' }>기획실</option>
						<option value="홍보과" ${ '홍보과' == vo.office ? 'selected' : '' }>홍보과</option>
						<option value="경리과" ${ '경리과' == vo.office ? 'selected' : '' }>경리과</option>
						<option value="회계과" ${ '회계과' == vo.office ? 'selected' : '' }>회계과</option>
						<option value="총무과" ${ '총무과' == vo.office ? 'selected' : '' }>총무과</option>
					</select>
					&nbsp;&nbsp;
					<input type="text" class="form-control" id="txtOffice" name="txtOffice" value="${vo.txtOffice}" maxlength=15 onclick='changeTxt(document.getElementById("office"))' />
					<div id="txtOfficeInvalid" class="invalid-feedback"></div>
			</div>
    </div>
    <div class="form-group" id="addressGroup">
			<label for="address">주소 : </label>
			<div class="input-group">
				<div class="input-group">
					<input type="text" class="input-group-prepend text-center" id="sample6_postcode" name="postcode" value="${vo.postCode}" size="10" placeholder="우편번호"  disabled>&nbsp;
					<input type="button" class="btn btn-info" id="btnPostCode" onclick="sample6_execDaumPostcode()" value="우편번호 찾기">&nbsp;
					<input type="text" class="form-control" id="sample6_address" name="roadAddress" value="${vo.roadAddr}" placeholder="도로명주소">&nbsp;
					<input type="text" class="form-control" id="sample6_extraAddress" name="extraAddress" value="${vo.extraAddr}" placeholder="지번주소">	
				</div>
				<div class="input-group">
					<input type="text" class="form-control mt-2" id="sample6_detailAddress" name="detailAddress" value="${vo.detailAddr}" placeholder="상세주소" >
					<div id="detailAddressInvalid" class="invalid-feedback"></div>
				</div>
			</div>
    </div>
    <div class="form-group" id="emailGroup">
			<label for="email1">Email address : &nbsp; &nbsp;<input type="button" value="이메일 중복체크" class="btn btn-info" onclick="emailCheck()"/></label>
			<div class="input-group">
				<input type="text" class="form-control" id="email1" name="email1" value="${email1}" maxlength=25 required />
				<font size="5pt" class="text-center text-info"><b>@</b></font>
				<div class="input-group-append" >
					<select id="email2" name="email2" class="custom-select" onchange='changeSel(this,updateForm.txtEmail2)'>
						<option value="-" > - 직접입력 - </option>
						<option value="naver.com"   <c:if test='${"naver.com" eq email2}'>selected</c:if> >naver.com</option>
						<option value="hanmail.net" <c:if test='${"hanmail.net" eq email2}'>selected</c:if> >hanmail.net</option>
						<option value="hotmail.com" <c:if test='${"hotmail.com" eq email2}'>selected</c:if> >hotmail.com</option>
						<option value="gmail.com"   <c:if test='${"gmail.com" eq email2}'>selected</c:if> >gmail.com</option>
						<option value="nate.com"    <c:if test='${"nate.com" eq email2}'>selected</c:if> >nate.com</option>
						<option value="yahoo.com"   <c:if test='${"yahoo.com" eq email2}'>selected</c:if> >yahoo.com</option>
					</select>
				</div>
				&nbsp;&nbsp;
				<c:choose>
					<c:when test='${"naver.com" eq email2 or "hanmail.net" eq email2 or "hotmail.com" eq email2 or "gmail.com" eq email2 or "nate.com" eq email2 or "yahoo.com" eq email2 }'>
						<input type="text" class="form-control" id="txtEmail2" name="txtEmail2" maxlength=25 onclick='changeTxt(updateForm.email2)' />
					</c:when>
					<c:otherwise>
						<input type="text" class="form-control" id="txtEmail2" name="txtEmail2" value="${email2}" maxlength=25 onclick='changeTxt(updateForm.email2)' />
					</c:otherwise>
				</c:choose>
				<div id="email1Invalid" class="invalid-feedback">이메일은 필수 입력사항입니다.</div>
				<div id="txtEmail2Invalid" class="invalid-feedback"></div>
				<input type="hidden" class="form-control" id="email" name="email" />
			</div>
		</div>
    <div class="form-group" id="telGroup">
			<label >전화번호 :</label> &nbsp;&nbsp;
			<div class="input-group">
				<div class="input-group-prepend">
					<select id="tel1" name="tel1" class="custom-select">
						<option value="02"  ${'02' ==tel1 ? 'selected' : '' } >02</option>
						<option value="031" ${'031'==tel1 ? 'selected' : '' } >031</option>
						<option value="032" ${'032'==tel1 ? 'selected' : '' } >032</option>
						<option value="041" ${'041'==tel1 ? 'selected' : '' } >041</option>
						<option value="042" ${'042'==tel1 ? 'selected' : '' } >042</option>
						<option value="043" ${'043'==tel1 ? 'selected' : '' } >043</option>
						<option value="051" ${'051'==tel1 ? 'selected' : '' } >051</option>
						<option value="052" ${'052'==tel1 ? 'selected' : '' } >052</option>
						<option value="061" ${'061'==tel1 ? 'selected' : '' } >061</option>
						<option value="062" ${'062'==tel1 ? 'selected' : '' } >062</option>
					</select>
				</div>
				_<div><input type="text" id="tel2" name="tel2" value="${tel2}" size=4 maxlength=4 class="form-control" required/></div>
				_<div><input type="text" id="tel3" name="tel3" value="${tel3}" size=4 maxlength=4 class="form-control" required/></div>
	 			<div id="blank" class="is-invalid"></div>
	  		<div id="tel2Invalid" class="invalid-feedback">전화번호의 가운데 자리는 필수 입력사항입니다.</div>
	 	 		<div id="tel3Invalid" class="invalid-feedback">전화번호의 마지막 자리는 필수 입력사항입니다.</div> 
	 			<input type="hidden" id="telNo" name="telNo" maxlength=13 class="form-control">
			</div> 
    </div>
    <div class="form-group" id="hpGroup">
			<label >휴대전화 :</label> &nbsp;&nbsp;
			<div class="input-group">
				<div class="input-group-prepend">
					<select id="hp1" name="hp1" class="custom-select">
						<option value="010" ${'010' == hp1 ? 'selected' : '' } >010</option>
						<option value="011" ${'011' == hp1 ? 'selected' : '' } >011</option>
						<option value="016" ${'016' == hp1 ? 'selected' : '' } >016</option>
						<option value="019" ${'019' == hp1 ? 'selected' : '' } >019</option>
						<option value="070" ${'070' == hp1 ? 'selected' : '' } >070</option>
					</select>
				</div>
				_<div><input type="text" id="hp2" name="hp2" value="${hp2}" size=4 maxlength=4 class="form-control" /></div>
				_<div><input type="text" id="hp3" name="hp3" value="${hp3}" size=4 maxlength=4 class="form-control" /></div>
	 			<div id="blank" class="is-invalid"></div>
	  		<div id="hp2Invalid" class="invalid-feedback"></div>
	 	 		<div id="hp3Invalid" class="invalid-feedback"></div> 
	 			<input type="hidden" id="hpNo" name="hpNo" maxlength=13 class="form-control">
			</div> 
    </div>
    <div class="form-group">
			<label for="memo">메모 : </label>
			<textarea rows="5" class="form-control" id="memo" name="memo"  placeholder="메모를 입력하세요" maxlength=500 >${vo.memo}</textarea>
			<div id="memoInvalid" class="invalid-feedback"></div>
    </div>
    <div class="form-group">
      기업 사진(파일용량:2MByte이내) :
			<input type="file" id="customImgFileName" name="customImgFileName" src="${vo.customImgFileName}" class="form-control-file border btn-lg p-0 mt-2"/>
			<div id="customImgFileNameInvalid" class="invalid-feedback"></div>
			<input type="hidden" id="photo" name="photo" />
    </div>
    <div class="form-group text-center">
	    <input type="button" class="btn btn-info" id="update" value="저장" onclick="checkUpdateForm()" />
	    <input type="reset" class="btn btn-info"  value="다시작성"/>&nbsp;
	    <input type="button" class="btn btn-info" value="돌아가기" onclick="location.href='${ctxPath}/customCompMain.cmbr';"/><br>
		</div>
  </form>
  <p><br/></p>
</div>
<br/>
<%@ include file="/include/footer.jsp" %>
</body>
</html>