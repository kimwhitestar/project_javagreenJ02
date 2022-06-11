package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.person.database.CustomPersonLoginDAO;

public class CustomPersonLogoutCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sLoginId = (String)session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		String sCustomName = (String)session.getAttribute("sCustomName");
		
		int res = new CustomPersonLoginDAO().updateLogout(sLoginId, sCustomId);//DB 로그아웃정보 수정
		if (1 == res) {
			session.invalidate();//세션삭제
			request.setAttribute("msg", "customPersonLogoutOk");
			request.setAttribute("url", request.getContextPath() + "/customPersonLogin.pmbr");
			request.setAttribute("customName", sCustomName);
		} else {
			request.setAttribute("msg", "customPersonLogoutNo");
			request.setAttribute("url", request.getContextPath() + "/customPersonMain.pmbr");
		}
	}
}