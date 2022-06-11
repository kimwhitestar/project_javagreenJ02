package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.person.database.CustomPersonLoginDAO;

public class CustomPersonDeletePractCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sLoginId = (String) session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		CustomPersonLoginDAO dao = new CustomPersonLoginDAO();
		//회원탈퇴 - 1달간은 회원정보유지, deleteDate와 logoutDate를 now()로 수정
		int res = dao.updateCustomCompLoginUserDel(sLoginId, sCustomId);
		
		if (1 == res) {
			request.setAttribute("msg", "customPersonDeletePractOk");
			request.setAttribute("url", request.getContextPath()+"/customPersonLogout.pmbr");
		} else {
			request.setAttribute("msg", "customPersonDeletePractNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonMain.pmbr");
		}
	}
}