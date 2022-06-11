package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.person.database.CustomPersonLoginDAO;

@WebServlet("/customPersonIdFindoutOk")//url패턴
public class CustomPersonIdFindoutOkCommand extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String hpNo = request.getParameter("hpNo");
		
		CustomPersonLoginDAO dao = new CustomPersonLoginDAO();
		String id = dao.searchId(email, hpNo, name);//회원ID찾기
		if (null == id) id = ""; //Ajax에서는 return값으로 null을 보낼 수 없으므로, 공백처리 후 리턴
		response.getWriter().write(id); //Ajax return 설정
	}
}
