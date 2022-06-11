package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompDAO;
import custom.comp.database.CustomCompLoginDAO;

public class CustomCompPwdChangeOkCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String companyNo = request.getParameter("companyNo");
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
		
		CustomCompLoginDAO loginDao = new CustomCompLoginDAO();
		CustomCompDAO compDao = new CustomCompDAO();
		//로그인 아이디 비존재
		if ( ! loginDao.loginIdCheck(id) ) {
			request.setAttribute("existIdYN", "N");
			request.setAttribute("msg", "customCompPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
			return;
		}
		//사업자번호 비존재
		if ( ! compDao.companyNoCheck(companyNo) ) {
			request.setAttribute("existCompanyNoYN", "N");
			request.setAttribute("msg", "customCompPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
			return;
		}
		//email 비존재시
		if ( ! compDao.emailCheck(email) ) {
			request.setAttribute("existEmailYN", "N");
			request.setAttribute("msg", "customCompPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
			return;
		}
		
		//email로 전송한 본인확인 암호화문자를 정상 입력받았는지 체크
//		if ( ! loginDao.searchShaCodeEmail(shaEmail) ) {
//			request.setAttribute("validShaCodeEmailYN", "N");
//			request.setAttribute("msg", "customCompPwdChangeNo");
//			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
//			return;
//		}
		
		//New비밀번호와 Re비밀번호가 같은지 체크
		if ( ! shaNewPwd.equals(shaRePwd) ) {
			request.setAttribute("validPwdYN", "N");
			request.setAttribute("msg", "customCompPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
			return;
		}
		
		int res = loginDao.updatePwd(id, companyNo, shaNewPwd);
		if (1 == res) {
			request.setAttribute("changedPwdYN", "Y");
			request.setAttribute("msg", "customCompPwdChangeOk");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
		} else {
			request.setAttribute("changedPwdYN", "N");
			request.setAttribute("msg", "customCompPwdChangeNo");
			request.setAttribute("url", request.getContextPath()+"/customCompPwdChange.cmbr");
		}
	}
}