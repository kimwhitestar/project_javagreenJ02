package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.database.CustomCompLoginDAO;

public class CustomCompDeletePractCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sLoginId = (String) session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		CustomCompLoginDAO dao = new CustomCompLoginDAO();
		//회원탈퇴 - 1달간은 회원정보유지, deleteDate와 logoutDate를 now()로 수정
		int res = dao.updateCustomCompLoginUserDel(sLoginId, sCustomId);
		
		if (1 == res) {
			request.setAttribute("msg", "customCompDeletePractOk");
			request.setAttribute("url", request.getContextPath()+"/customCompLogout.cmbr");
		} else {
			request.setAttribute("msg", "customCompDeletePractNo");
			request.setAttribute("url", request.getContextPath()+"/customCompMain.cmbr");
		}
	}
}