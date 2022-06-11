package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompLoginDAO;

@WebServlet("/customCompIdFindoutOk")//url패턴
public class CustomCompIdFindoutOkCommand extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customName = request.getParameter("customName");
		String email = request.getParameter("email");
		String telNo = request.getParameter("telNo");
		
		CustomCompLoginDAO dao = new CustomCompLoginDAO();
		String id = dao.searchId(email, telNo, customName);//회원ID찾기
		if (null == id) id = ""; //Ajax에서는 return값으로 null을 보낼 수 없으므로, 공백처리 후 리턴
		response.getWriter().write(id); //Ajax return 설정
	}
}