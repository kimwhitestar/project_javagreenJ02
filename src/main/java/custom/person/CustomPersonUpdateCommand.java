package custom.person;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.kind.database.CustomKindDAO;
import custom.kind.database.CustomKindVO;
import custom.person.database.CustomPersonDAO;
import custom.person.database.CustomPersonVO;

public class CustomPersonUpdateCommand implements CustomPersonInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//개별회원정보 조회
		CustomPersonVO vo = new CustomPersonDAO().search( (int) request.getSession().getAttribute("sCustomId") );
		
		//Form에 출력을 위한 분리작업
		//직업 편집
		int startIdx = vo.getJob().indexOf(":") + 1;
		if ( -1 < startIdx ) {
			vo.setTxtJob(vo.getJob().substring(startIdx, vo.getJob().length()));
			vo.setJob("기타");
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
		
		//생일
		vo.setBirthDate(vo.getBirthDate().substring(0, 10));

		//전화번호 분리(-) : 선택입력항목
		String[] tel = vo.getTelNo().split("-");
		if (null == tel || 3 > tel.length) {
			request.setAttribute("tel1", "");
			request.setAttribute("tel2", "");
			request.setAttribute("tel3", "");
		} else {
			request.setAttribute("tel1", tel[0]);
			request.setAttribute("tel2", tel[1]);
			request.setAttribute("tel3", tel[2]);
		}		
		
		//휴대전화 분리(-) : 필수입력항목
		String[] hp = vo.getHpNo().split("-");
		request.setAttribute("hp1", hp[0]);
		request.setAttribute("hp2", hp[1]);
		request.setAttribute("hp3", hp[2]);

		request.setAttribute("gender", String.valueOf(vo.getGender()));

		//개인고객고분코드 목록조회 
		CustomKindDAO dao = new CustomKindDAO();
		List<CustomKindVO> customKindVo = dao.searchCustomPersonCustomKindList();
		request.setAttribute("customKindVo", customKindVo);
		
		request.setAttribute("vo", vo);
	}
}