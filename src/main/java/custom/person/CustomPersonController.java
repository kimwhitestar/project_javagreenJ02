package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.CustomCompEmailCheckCommand;
import custom.comp.CustomCompIdFindoutCommand;

@SuppressWarnings("serial")
@WebServlet("*.pmbr")
public class CustomPersonController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomPersonInterface command = null;
		String viewPage = "/WEB-INF";
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
		
		//세션이 끊겼으면, 회원레벨을 비회원으로 바꿔서 작업의 진행을 로그인창으로 보낸다.
		HttpSession session = request.getSession();
		int sGradeCode = session.getAttribute("sGradeCode")==null ? 99 : (char) session.getAttribute("sGradeCode");
		
		//로그인
		if (com.equals("customPersonLogin")) {
			command = new CustomPersonLoginCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonLogin.jsp";
		}
		//로그인OK
		else if (com.equals("customPersonLoginOk")) {
			command = new CustomPersonLoginOkCommand();
			command.execute(request, response);
			viewPage = "/message/customPersonMessage.jsp";
		}
		//로그인 Main
		else if (com.equals("customPersonMain")) {
			command = new CustomPersonMainCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonMain.jsp";
		}
		//아이디 찾기
		else if (com.equals("customPersonIdFindout")) {
			command = new CustomPersonIdFindoutCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonIdFindout.jsp";
		}
		//로그아웃
		else if (com.equals("customPersonLogout")) {
			command = new CustomPersonLogoutCommand();
			command.execute(request, response);
			viewPage = "/message/customPersonMessage.jsp";
		}
		//ID 중복체크
		else if (com.equals("customPersonLoginIdCheck")) {
			command = new CustomPersonLoginIdCheckCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonLoginIdCheck.jsp";
		}
		//이메일 중복체크
		else if (com.equals("customPersonEmailCheck")) {
			command = new CustomPersonEmailCheckCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonEmailCheck.jsp";
		}
		//주민등록번호 중복체크
		else if (com.equals("customPersonJuminNoCheck")) {
			command = new CustomPersonJuminNoCheckCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonJuminNoCheck.jsp";
		}
		//회원가입
		else if (com.equals("customPersonEntry")) {
			command = new CustomPersonEntryCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonEntry.jsp";
		}
		//회원가입OK
		else if (com.equals("customPersonEntryOk")) {
			command = new CustomPersonEntryOkCommand();
			command.execute(request, response);
			viewPage = "/message/customPersonMessage.jsp";
		}
		//회원정보수정
		else if (com.equals("customPersonUpdate")) {
			command = new CustomPersonUpdateCommand();
			command.execute(request, response);
			viewPage += "/custom/person/customPersonUpdate.jsp";
		}
		//회원정보수정OK
		else if (com.equals("customPersonUpdateOk")) {
			command = new CustomPersonUpdateOkCommand();
			command.execute(request, response);
			viewPage = "/message/customPersonMessage.jsp";
		}
		//회원탈퇴-회원로그인불허(회원삭제 대기기간 : 30일)
		else if (com.equals("customPersonDeletePract")) {
			command = new CustomPersonDeletePractCommand();
			command.execute(request, response);
			viewPage = "/message/customPersonMessage.jsp";
		}
//	//비밀번호 찾기/수정
//	else if (com.equals("customPersonPwdChange")) {
////		command = new CustomPersonPwdChangeCommand();
////		command.execute(request, response);
//		viewPage += "/custom/person/customPersonPwdChange.jsp";
//	}
//	//비밀번호 찾기/수정Ok
//	else if (com.equals("customPersonPwdChangeOk")) {
//		command = new CustomPersonPwdChangeOkCommand();
//		command.execute(request, response);
//		viewPage = "/message/customPersonMessage.jsp";
//	}
//		else if (4 < level) {
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/");
//			dispatcher.forward(request, response);
//		}
		
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}