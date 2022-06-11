package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.person.database.CustomPersonDAO;

public class CustomPersonJuminNoCheckCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String juminNo = request.getParameter("juminNo");
		request.setAttribute("juminNo", juminNo);
		request.setAttribute("existJuminNoYN", null);
		CustomPersonDAO dao = new CustomPersonDAO();
		if (null != juminNo) {
			//isExist = true 아이디 중복
			if (dao.juminNoCheck(juminNo)) {
				request.setAttribute("existJuminNoYN", "Y");
			} else {
				request.setAttribute("existJuminNoYN", "N");
			}
		}
	}
}