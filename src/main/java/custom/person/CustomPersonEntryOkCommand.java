package custom.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.person.database.CustomPersonDAO;
import custom.person.database.CustomPersonLoginDAO;
import custom.person.database.CustomPersonLoginVO;
import custom.person.database.CustomPersonVO;

public class CustomPersonEntryOkCommand implements CustomPersonInterface {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CustomPersonLoginDAO loginDao = new CustomPersonLoginDAO();
		CustomPersonLoginVO loginVo = new CustomPersonLoginVO();
		
		CustomPersonDAO personDao = new CustomPersonDAO();
		CustomPersonVO personVo = new CustomPersonVO();

		//개인고객아이디 발급
		//CUSTOM_ID 구성 : 3자리(100~999) 'CUSTOM_KIND_CD' + 5자리 '순차발행' (00001~99999))
		//CUSTOM_KIND_CD '1', '2'의 경우는 '100'으로 설정 
		int customKindCode = Integer.parseInt(request.getParameter("customKindCode"));
		int customId = personDao.obtainCustomId(customKindCode);

		//개인고객 회원정보 VO 설정
		personVo.setCustomId(customId);
		personVo.setCustomNm(request.getParameter("name"));
		personVo.setCustomKindCd(customKindCode);
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

		//개인고객 회원정보 DB 등록
		int resPerson = personDao.insert(personVo);
		
		//개인고객 로그인 VO 설정
		loginVo.setId(request.getParameter("id"));
		loginVo.setShaPwd(request.getParameter("shaPwd"));
		loginVo.setCustomId(customId);
		
		//개인고객 로그인 DB 등록
		int resLogin = loginDao.insert(loginVo);
		
		if (1 == resLogin && 1 == resPerson) {
			request.setAttribute("msg", "customPersonEntryOk");
			request.setAttribute("url", request.getContextPath()+"/customPersonLogin.pmbr");
		} else {
			request.setAttribute("msg", "customPersonEntryNo");
			request.setAttribute("url", request.getContextPath()+"/customPersonEntry.pmbr");
		}
	}
}