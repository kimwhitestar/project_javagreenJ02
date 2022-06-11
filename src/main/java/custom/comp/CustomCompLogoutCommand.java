package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.database.CustomCompLoginDAO;

public class CustomCompLogoutCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sLoginId = (String)session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		String sCustomName = (String)session.getAttribute("sCustomName");
		
		int res = new CustomCompLoginDAO().updateLogout(sLoginId, sCustomId);//DB 로그아웃정보 수정
		if (1 == res) {
			session.invalidate();//세션삭제
			request.setAttribute("msg", "customCompLogoutOk");
			request.setAttribute("url", request.getContextPath() + "/customCompLogin.cmbr");
			request.setAttribute("customName", sCustomName);
		} else {
			request.setAttribute("msg", "customCompLogoutNo");
			request.setAttribute("url", request.getContextPath() + "/customCompMain.cmbr");
		}
	}
}