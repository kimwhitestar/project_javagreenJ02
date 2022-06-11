<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <title>javagreenJ_khv(김흰별 물류관리프로젝트)</title>
  <meta charset="utf-8">
  <%@ include file="/include/bs4.jsp" %>
  <style></style>
  <script>
  	'use strict';
  </script>
</head>
	<frameset cols="32%, 68%">
		<frame src="${ctxPath}/include/customNav.jsp" name="customLeft" id="customLeft" frameborder="0" />
		<frame src="${ctxPath}/customPersonLogin.pmbr" name="customContent" id="customContent" frameborder="0" />
	</frameset>
</html>