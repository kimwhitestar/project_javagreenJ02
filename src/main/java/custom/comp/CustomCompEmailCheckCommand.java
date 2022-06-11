package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompDAO;

public class CustomCompEmailCheckCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		request.setAttribute("email", email);
		request.setAttribute("email1", request.getParameter("email1"));
		request.setAttribute("email2", request.getParameter("email2"));
		request.setAttribute("txtEmail2", request.getParameter("txtEmail2"));
		request.setAttribute("existEmailYN", null);
		CustomCompDAO dao = new CustomCompDAO();
		if (null != email) {
			//이메일 중복 / 존재 체크
			if (dao.emailCheck(email)) {
				request.setAttribute("existEmailYN", "Y");
			} else {
				request.setAttribute("existEmailYN", "N");
			}
		}
	}
}