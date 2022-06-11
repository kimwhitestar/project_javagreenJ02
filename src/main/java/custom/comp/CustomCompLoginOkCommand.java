package custom.comp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import custom.comp.database.CustomCompLoginDAO;
import custom.comp.database.CustomCompLoginVO;

public class CustomCompLoginOkCommand implements CustomCompInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");//login요청-id
		String shaPwd = request.getParameter("shaPwd");//login요청-shaPwd
		String idSave = request.getParameter("idSave");//login요청-idSave저장(id쿠키저장)
		HttpSession session = request.getSession();
		
		// --------------------------------------------------
		// 로그인 성공시 처리 내용
		// --------------------------------------------------
		// 1.오늘방문횟수, 전체방문횟수 1씩 증가 
		// 2.포인터 증가(1일 10회까지 방문시마다 100포인트씩 증가)
		// 3.주요자료 세션 저장 
		// 4.아이디 저장유무에 따라 쿠키 저장
		// --------------------------------------------------
		CustomCompLoginDAO dao = new CustomCompLoginDAO();
		CustomCompLoginVO vo = dao.searchCustomCompLogin(id, shaPwd);

		//로그인 아이디,비밀번호로 회원조회가 안됬을 경우, 로그인 화면으로 이동
		if (null == vo) {
			request.setAttribute("msg", "customCompLoginNo");
			request.setAttribute("url", request.getContextPath() + "/customCompLogin.cmbr");
			return;
		}
		//로그인 아이디,비밀번호로 회원조회가 됬을 경우, HttpSession에 조회된 회원정보 설정
		session.setAttribute("sLoginId", vo.getId());
		session.setAttribute("sGradeCode", vo.getCustomGrade());//고객등급
		session.setAttribute("sGradeName", vo.getGradeName());//고객등급명
		session.setAttribute("sCustomId", vo.getCustomId());//고객ID -- SEQ로 바꾸자
		session.setAttribute("sCustomName", vo.getCustomName());//고객명
		session.setAttribute("sLoginDate", vo.getLoginDate());//로그인날짜
		
		// --------------------------------------------------
		// DB 저장 : 오늘방문횟수, 전체방문횟수, 포인터 100씩 증가
		// --------------------------------------------------
		//최종방문일과 오늘날짜 비교해서 다른 경우, 오늘방문횟수(todayCnt)값을 0으로 초기화
    String todayYmdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    String todayYmd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if (null == vo.getLoginDate() || ! vo.getLoginDate().substring(0, 10).equals(todayYmd)) {
			dao.updateTodayCnt(vo.getId(), vo.getCustomId());//DB저장(오늘방문횟수 '0', 로그인날짜 default now())
			vo.setTodayCnt(0);
			vo.setLoginDate(todayYmdhms);
		}
		//1.오늘방문횟수, 전체방문횟수 1씩 증가 
		dao.updateVisitCntAndTodayCnt(vo.getId(), vo.getCustomId());//DB 방문횟수 증가
		vo.setTodayCnt(vo.getTodayCnt() + 1);
		vo.setVisitCnt(vo.getVisitCnt() + 1);
		if (10 >= vo.getTodayCnt()) {
			//2.포인터 100씩 증가(방문시마다 100포인트씩 증가<DB저장>, 1일 10회 이하)
			dao.updatePoint(vo.getId(), vo.getCustomId());//DB 포인트 100포인트 증가
			vo.setPoint(vo.getPoint() + 100);
		}
		// --------------------------------------------------
		// 세션 저장(Mypage 회원전용방 출력용) : 오늘방문횟수, 전체방문횟수, 포인트
		// --------------------------------------------------
		session.setAttribute("sTodayVCnt", vo.getTodayCnt());
		session.setAttribute("sVCnt", vo.getVisitCnt());
		session.setAttribute("sPoint", vo.getPoint());
		
		//idSave체크시 : 쿠키에 아이디(id)를 저장 checkbox checked 클릭 여부 - on/null
		Cookie cookie = new Cookie("cLoginId", id);
		if (null != idSave && idSave.equals("on")) 
			cookie.setMaxAge(60*60*24*7); //쿠키 만료시간은 7일
		else 
			cookie.setMaxAge(0); //쿠키 삭제
		response.addCookie(cookie);

		request.setAttribute("msg", "customCompLoginOk");
		request.setAttribute("url", request.getContextPath() + "/customCompMain.cmbr");
	}
}