package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompLoginDAO;

public class CustomCompLoginIdCheckCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("existIdYN", null);
		CustomCompLoginDAO dao = new CustomCompLoginDAO();
		if (null != id) {
			//isExist = true 아이디 중복
			if (dao.loginIdCheck(id)) {
				request.setAttribute("existIdYN", "Y");
			} else {
				request.setAttribute("existIdYN", "N");
			}
		}
	}
}