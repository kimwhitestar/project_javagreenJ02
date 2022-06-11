package custom.comp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.comp.database.CustomCompDAO;
import custom.comp.database.CustomCompLoginDAO;
import custom.comp.database.CustomCompLoginVO;
import custom.comp.database.CustomCompVO;

public class CustomCompEntryOkCommand implements CustomCompInterface {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomCompLoginDAO loginDao = new CustomCompLoginDAO();
		CustomCompLoginVO loginVo = new CustomCompLoginVO();
		
		CustomCompDAO compDao = new CustomCompDAO();
		CustomCompVO compVo = new CustomCompVO();
		
		//기업고객아이디 발급
		//CUSTOM_ID 구성 : 3자리(100~999) 'CUSTOM_KIND_CD' + 5자리 '순차발행' (00001~99999))
		//CUSTOM_KIND_CD '1', '2'의 경우는 '100'으로 설정 
		int customKindCode = Integer.parseInt(request.getParameter("customKindCode"));
		int customId = compDao.obtainCustomId(customKindCode);

		//기업고객 회원정보 VO 설정
		compVo.setCustomId(customId);
		compVo.setCustomNm(request.getParameter("customName"));
		compVo.setCustomNmShort(request.getParameter("customNameShort"));
		compVo.setCustomKindCd(customKindCode);
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
		compVo.setCustomId(customId);

		//기업고객 회원정보 DB 등록
		int resComp = compDao.insert(compVo);
		
		//기업고객 로그인 VO 설정
		loginVo.setId(request.getParameter("id"));
		loginVo.setShaPwd(request.getParameter("shaPwd"));
		loginVo.setCustomId(customId);
		
		//기업고객 로그인 DB 등록
		int resLogin = loginDao.insert(loginVo);
		
		if (1 == resLogin && 1 == resComp) {
			request.setAttribute("msg", "customCompEntryOk");
			request.setAttribute("url", request.getContextPath()+"/customCompLogin.cmbr");
		} else {
			request.setAttribute("msg", "customCompEntryNo");
			request.setAttribute("url", request.getContextPath()+"/customCompEntry.cmbr");
		}
	}
}