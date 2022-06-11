package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.database.CustomCompVO;
import custom.person.database.CustomPersonDAO;
import custom.person.database.CustomPersonLoginDAO;
import custom.person.database.CustomPersonLoginVO;
import custom.person.database.CustomPersonVO;

public class CustomPersonUpdateOkCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//개인고객 로그인 정보 취득
		String sLoginId = (String) session.getAttribute("sLoginId");
		String shaPwd = request.getParameter("shaPwd");
		
		//개인고객 로그인 정보 조회
		CustomPersonLoginDAO loginDao = new CustomPersonLoginDAO();
		CustomPersonLoginVO loginVo = loginDao.searchCustomPersonLogin(sLoginId, shaPwd);

		//개인고객 회원정보 VO 설정
		CustomPersonVO personVo = new CustomPersonVO();
		String name = request.getParameter("name");
		personVo.setCustomId(loginVo.getCustomId());
		personVo.setCustomNm(name);
		personVo.setCustomKindCd(Integer.parseInt(request.getParameter("customKindCode")));
		personVo.setJob(request.getParameter("job"));
		personVo.setTxtJob(request.getParameter("txtJob"));
		personVo.setPostCode(request.getParameter("postcode"));
		personVo.setRoadAddr(request.getParameter("roadAddress"));
		personVo.setExtraAddr(request.getParameter("extraAddress"));
		personVo.setDetailAddr(request.getParameter("detailAddress"));
		personVo.setEmail(request.getParameter("email"));
		personVo.setJuminNo(request.getParameter("juminNo"));
		personVo.setGender(request.getParameter("gender").charAt(0));
		personVo.setBirthDate(request.getParameter("birthDate"));
		personVo.setTelNo(request.getParameter("telNo"));
		personVo.setHpNo(request.getParameter("hpNo"));
		personVo.setHobby(request.getParameter("checkedHobbies"));
		personVo.setMemo(request.getParameter("memo"));

		//개인고객 회원정보 DB 수정
		CustomPersonDAO personDao = new CustomPersonDAO();
		int resPerson = personDao.update(personVo);
		
		if (1 == resPerson) {
			session.setAttribute("sCustomName", name);//고객명
			request.setAttribute("msg", "customPersonUpdateOk");
			request.setAttribute("url", request.getContextPath()+"/customPersonUpdate.pmbr");
		} else {
			request.setAttribute("msg", "customPersonUpdateNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonUpdate.pmbr");
		}
	}
}