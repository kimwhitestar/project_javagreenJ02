package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.person.database.CustomPersonDAO;
import custom.person.database.CustomPersonLoginDAO;

public class CustomPersonPwdChangeOkCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String juminNo = request.getParameter("juminNo");
		String shaNewPwd = request.getParameter("shaNewPwd");
		String shaRePwd = request.getParameter("shaRePwd");
		String email = request.getParameter("email");
		//#################################################
		//이메일로 전송받은 암호화문자 확인용(WAS서버에서대기시간필요ㅜㅜ)
		//Login DB에 암호화코드이메일 컬럼 추가, 클라이언트에게 암호화코드이메일 발송시, DB update
		//클라이언트로 부터 암호화코드이메일 확인시, DB select로 확인 필요
		//String shaCodeEmail = request.getParameter("shaCodeEmail");
		//new SecurityUtil().encriptShaCodeEmailRandom();//암호화 문자
		//#################################################
		
		CustomPersonLoginDAO loginDao = new CustomPersonLoginDAO();
		CustomPersonDAO personDao = new CustomPersonDAO();
		//로그인 아이디 비존재
		if ( ! loginDao.loginIdCheck(id) ) {
			request.setAttribute("existIdYN", "N");			
			request.setAttribute("msg", "customPersonPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonPwdChange.pmbr");
			return;
		}
		//주민등록번호 비존재
		if ( ! personDao.juminNoCheck(juminNo) ) {
			request.setAttribute("existJuminNoYN", "N");
			request.setAttribute("msg", "customCompPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
			return;
		}
		//email 비존재시
		if ( ! personDao.emailCheck(email) ) {
			request.setAttribute("existEmailYN", "N");
			request.setAttribute("msg", "customPersonPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonPwdChange.pmbr");
			return;
		}

		//email로 전송한 본인확인 암호화문자를 정상 입력받았는지 체크
//	if ( ! loginDao.searchShaCodeEmail(shaEmail) ) {
//		request.setAttribute("validShaCodeEmailYN", "N");
//		request.setAttribute("msg", "customPersonPwdChangeNo");
//		request.setAttribute("url", request.getContextPath()+"/customPersonPwdChange.cmbr");
//		return;
//	}
		
		//New비밀번호와 Re비밀번호가 같은지 체크
		if ( ! shaNewPwd.equals(shaRePwd) ) {
			request.setAttribute("validPwdYN", "N");
			request.setAttribute("msg", "customPersonPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonPwdChange.pmbr");
			return;
		}
		
		int res = loginDao.updatePwd(id, juminNo, shaNewPwd);
		if (1 == res) {
			request.setAttribute("changedPwdYN", "Y");
			request.setAttribute("msg", "customPersonPwdChangeOk");
			request.setAttribute("url", request.getContextPath()+"/customPersonPwdChange.pmbr");
		} else {
			request.setAttribute("changedPwdYN", "N");
			request.setAttribute("msg", "customPersonPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonPwdChange.pmbr");
		}
	}
}