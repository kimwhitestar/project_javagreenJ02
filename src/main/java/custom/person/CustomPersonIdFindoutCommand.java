package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.person.database.CustomPersonDAO;

public class CustomPersonIdFindoutCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomPersonDAO dao = new CustomPersonDAO();
		String[] emailDomain = dao.searchEmailDomain();
		request.setAttribute("emailDomain", emailDomain);
	}
}