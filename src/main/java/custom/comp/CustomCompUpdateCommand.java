package custom.comp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.database.CustomCompDAO;
import custom.comp.database.CustomCompVO;
import custom.kind.database.CustomKindDAO;
import custom.kind.database.CustomKindVO;

public class CustomCompUpdateCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		//개별회원정보 조회
		CustomCompVO vo = new CustomCompDAO().search(sCustomId);
		
		//Form에 출력을 위한 분리작업
		//사무실 편집
		int startIdx = vo.getOffice().indexOf(":") + 1;
		if ( -1 < startIdx ) {
			vo.setTxtOffice(vo.getOffice().substring(startIdx, vo.getOffice().length()));
			vo.setOffice("기타");
		}
		
		//Email 분리(@)
		String[] email = vo.getEmail().split("@");
		if (null == email || 2 > email.length) {
			request.setAttribute("email1", "");
			request.setAttribute("email2", "");
		} else {
			request.setAttribute("email1", email[0]);
			request.setAttribute("email2", email[1]);
		}
		
		//창립일
System.out.println("vo.getEstblDate() = " + vo.getEstblDate());		
		vo.setEstblDate(vo.getEstblDate().substring(0, 10));

		//전화번호 분리(-) : 필수입력항목
		String[] tel = vo.getTelNo().split("-");
		request.setAttribute("tel1", tel[0]);
		request.setAttribute("tel2", tel[1]);
		request.setAttribute("tel3", tel[2]);
		
		//휴대전화 분리(-) : 선택입력항목
		String[] hp = vo.getHpNo().split("-");
		if (null == hp || 3 > hp.length) {
			request.setAttribute("hp1", "");
			request.setAttribute("hp2", "");
			request.setAttribute("hp3", "");
		} else {
			request.setAttribute("hp1", hp[0]);
			request.setAttribute("hp2", hp[1]);
			request.setAttribute("hp3", hp[2]);
		}
		
		//기업고객고분코드 목록조회 
		CustomKindDAO dao = new CustomKindDAO();
		List<CustomKindVO> customKindVo = dao.searchCustomCompCustomKindList();
		request.setAttribute("customKindVo", customKindVo);
		
		request.setAttribute("vo", vo);
	}
}