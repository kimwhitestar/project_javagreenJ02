package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("*.cmbr")
public class CustomCompController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomCompInterface command = null;
		String viewPage = "/WEB-INF";
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
		
		//세션이 끊겼으면, 회원레벨을 비회원으로 바꿔서 작업의 진행을 로그인창으로 보낸다.
		HttpSession session = request.getSession();
		int sGradeCode = session.getAttribute("sGradeCode")==null ? 99 : (char) session.getAttribute("sGradeCode");
		
		//로그인
		if (com.equals("customCompLogin")) {
			command = new CustomCompLoginCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompLogin.jsp";
		}
		//로그인OK
		else if (com.equals("customCompLoginOk")) {
			command = new CustomCompLoginOkCommand();
			command.execute(request, response);
			viewPage = "/message/customCompMessage.jsp";
		}
		//아이디 찾기
		else if (com.equals("customCompIdFindout")) {
			command = new CustomCompIdFindoutCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompIdFindout.jsp";
		}
		//로그인 Main
		else if (com.equals("customCompMain")) {
			command = new CustomCompMainCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompMain.jsp";
		}
		//로그아웃
		else if (com.equals("customCompLogout")) {
			command = new CustomCompLogoutCommand();
			command.execute(request, response);
			viewPage = "/message/customCompMessage.jsp";
		}
		//ID 중복체크
		else if (com.equals("customCompLoginIdCheck")) {
			command = new CustomCompLoginIdCheckCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompLoginIdCheck.jsp";
		}
		//사업자등록번호 중복체크
		else if (com.equals("customCompCompanyNoCheck")) {
			command = new CustomCompCompanyNoCheckCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompCompanyNoCheck.jsp";
		}
		//이메일 중복체크
		else if (com.equals("customCompEmailCheck")) {
			command = new CustomCompEmailCheckCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompEmailCheck.jsp";
		}
		//회원가입
		else if (com.equals("customCompEntry")) {
			command = new CustomCompEntryCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompEntry.jsp";
		}
		//회원가입OK
		else if (com.equals("customCompEntryOk")) {
			command = new CustomCompEntryOkCommand();
			command.execute(request, response);
			viewPage = "/message/customCompMessage.jsp";
		}
		//회원정보수정
		else if (com.equals("customCompUpdate")) {
			command = new CustomCompUpdateCommand();
			command.execute(request, response);
			viewPage += "/custom/comp/customCompUpdate.jsp";
		}
		//회원정보수정OK
		else if (com.equals("customCompUpdateOk")) {
			command = new CustomCompUpdateOkCommand();
			command.execute(request, response);
			viewPage = "/message/customCompMessage.jsp";
		}
		//회원탈퇴-회원로그인불허(회원삭제 대기기간 : 30일)
		else if (com.equals("customCompDeletePract")) {
			command = new CustomCompDeletePractCommand();
			command.execute(request, response);
			viewPage = "/message/customCompMessage.jsp";
		}
		
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}