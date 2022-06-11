package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompDAO;

public class CustomCompIdFindoutCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomCompDAO dao = new CustomCompDAO();
		String[] emailDomain = dao.searchEmailDomain();
		request.setAttribute("emailDomain", emailDomain);
	}
}