package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.database.CustomCompDAO;
import custom.comp.database.CustomCompLoginDAO;
import custom.comp.database.CustomCompLoginVO;
import custom.comp.database.CustomCompVO;

public class CustomCompUpdateOkCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//기업고객 로그인 VO 설정
		String sLoginId = (String) session.getAttribute("sLoginId");
		String shaPwd = request.getParameter("shaPwd");
		
		//기업고객 로그인 정보 조회
		CustomCompLoginDAO loginDao = new CustomCompLoginDAO();
		CustomCompLoginVO loginVo = loginDao.searchCustomCompLogin(sLoginId, shaPwd);
		
		//기업고객 회원정보 VO 설정
		CustomCompVO compVo = new CustomCompVO();
		String customName = request.getParameter("customName");
		compVo.setCustomId(loginVo.getCustomId());
		compVo.setCustomNm(customName);
		compVo.setCustomNmShort(request.getParameter("customNameShort"));
		compVo.setCustomKindCd(Integer.parseInt(request.getParameter("customKindCode")));
		compVo.setEstblDate(request.getParameter("estblDate"));
		compVo.setCompanyNo(request.getParameter("companyNo"));
		compVo.setOffice(request.getParameter("office"));
		compVo.setTxtOffice(request.getParameter("txtOffice"));
		compVo.setTelNo(request.getParameter("telNo"));
		compVo.setHpNo(request.getParameter("hpNo"));
		compVo.setEmail(request.getParameter("email"));
		compVo.setPostCode(request.getParameter("postcode"));
		compVo.setRoadAddr(request.getParameter("roadAddress"));
		compVo.setExtraAddr(request.getParameter("extraAddress"));
		compVo.setDetailAddr(request.getParameter("detailAddress"));
		compVo.setMemo(request.getParameter("memo"));
		compVo.setCustomImgFileName(request.getParameter("photo"));

		//기업고객 회원정보 DB 수정
		CustomCompDAO compDao = new CustomCompDAO();
		int resComp = compDao.update(compVo);
		
		if (1 == resComp) {
			session.setAttribute("sCustomName", customName);//고객명
			request.setAttribute("msg", "customCompUpdateOk");
			request.setAttribute("url", request.getContextPath()+"/customCompUpdate.cmbr");
		} else {
			request.setAttribute("msg", "customCompUpdateNo");
			request.setAttribute("url", request.getContextPath()+"/customCompUpdate.cmbr");
		}
	}
}