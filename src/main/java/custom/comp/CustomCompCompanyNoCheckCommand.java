package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompDAO;

public class CustomCompCompanyNoCheckCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String companyNo = request.getParameter("companyNo");
		request.setAttribute("companyNo", companyNo);
		request.setAttribute("existCompanyNoYN", null);
		CustomCompDAO dao = new CustomCompDAO();
		if (null != companyNo) {
			//isExist = true 아이디 중복
			if (dao.companyNoCheck(companyNo)) {
				request.setAttribute("existCompanyNoYN", "Y");
			} else {
				request.setAttribute("existCompanyNoYN", "N");
			}
		}
	}
}