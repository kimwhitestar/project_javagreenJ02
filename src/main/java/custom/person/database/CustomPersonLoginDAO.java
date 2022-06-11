package custom.person.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.MysqlConn;

public class CustomPersonLoginDAO {
	private final MysqlConn instance = MysqlConn.getInstance();
	private final Connection conn = instance.getConn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private CustomPersonLoginVO loginVo = null;
	private String sql = new String("");

	//개인회원로그인등록 - 가입포인트 1000 (default)
	public int insert(CustomPersonLoginVO vo) {
		int res = 0, idx = 0;
		try {
			sql = "INSERT INTO CUSTOM_PERSON_LOGIN VALUES ( ?, ?, ?, 'P', DEFAULT, DEFAULT, DEFAULT, NULL, NULL, NULL, NULL, DEFAULT, ?, NULL, NULL, NULL, NULL )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++idx , vo.getId());//primary key
			pstmt.setString(++idx , vo.getShaPwd());
			pstmt.setInt(++idx , vo.getCustomId());//foreign key(unique key)
			pstmt.setString(++idx , vo.getId());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
	
	// 로그인 
	public CustomPersonLoginVO searchCustomPersonLogin(String id, String shaPwd) {
		try {
			sql = "SELECT "
					+ " ID," //primary key
					+ " PWD,"
					+ " CUSTOM_ID," //foreign key (custom_person TBL)
					+ " (SELECT CUSTOM_NM FROM CUSTOM_PERSON WHERE CUSTOM_ID = CUSTOM_PERSON_LOGIN.CUSTOM_ID) AS CUSTOM_NAME," //foreign key (코드TBL)
					+ " CUSTOM_GRADE,"
					+ " (SELECT GRADE_NAME FROM CUSTOM_GRADE WHERE GRADE_CODE = CUSTOM_GRADE) AS GRADE_NAME," //foreign key (코드TBL)
					+ " POINT,"
					+ " VISIT_CNT,"
					+ " TODAY_CNT,"
					+ " LOGIN_DATE,"
					+ " LOGOUT_DATE,"
					+ " CREATE_DATE,"
					+ " UPDATE_DATE,"
					+ " DELETE_DATE "
					+ "FROM CUSTOM_PERSON_LOGIN "
					+ "WHERE ID = ? AND PWD = ? AND DELETE_DATE IS NULL "; //primary key(id) indexing
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, shaPwd);//비밀번호 암호화 처리
			rs = pstmt.executeQuery();
			if (rs.next()) { //1개 검색된 ResultSet DTO의 레코드로 이동 
				loginVo = new CustomPersonLoginVO();
				loginVo.setId(rs.getString("ID"));
				loginVo.setShaPwd(rs.getString("PWD"));
				loginVo.setCustomId(rs.getInt("CUSTOM_ID"));
				loginVo.setCustomName(rs.getString("CUSTOM_NAME"));
				loginVo.setCustomGrade(rs.getString("CUSTOM_GRADE").charAt(0));
				loginVo.setGradeName(rs.getString("GRADE_NAME"));
				loginVo.setPoint(rs.getInt("POINT"));
				loginVo.setVisitCnt(rs.getInt("VISIT_CNT"));
				loginVo.setTodayCnt(rs.getInt("TODAY_CNT"));
				loginVo.setLoginDate(rs.getString("LOGIN_DATE"));
				loginVo.setLogoutDate(rs.getString("LOGOUT_DATE"));
				loginVo.setCreateDate(rs.getString("CREATE_DATE"));
				loginVo.setUpdateDate(rs.getString("UPDATE_DATE"));
				loginVo.setDeleteDate(rs.getString("DELETE_DATE"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return loginVo;
	}

	//오늘방문횟수 = 0(초기화) - login
	public int updateTodayCnt(String id, int customId) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON_LOGIN SET TODAY_CNT = 0, LOGIN_DATE = DEFAULT, LOGIN_USER = ?, UPDATE_DATE = DEFAULT, UPDATE_USER = ? WHERE ID = ? AND CUSTOM_ID = ? AND DELETE_DATE IS NULL ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			pstmt.setString(3, id);
			pstmt.setInt(4, customId);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}

	// 오늘방문횟수 1회씩 증가 - login
	// 전체방문횟수 1회씩 증가 - login
	public int updateVisitCntAndTodayCnt(String id, int customId) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON_LOGIN SET VISIT_CNT = VISIT_CNT+1, TODAY_CNT = TODAY_CNT+1, UPDATE_DATE = DEFAULT, UPDATE_USER = ? WHERE ID = ? AND CUSTOM_ID = ? AND DELETE_DATE IS NULL ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			pstmt.setInt(3, customId);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
	
	// 100 포인트씩 증가 - login
	public int updatePoint(String id, int customId) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON_LOGIN SET POINT = POINT + 100, UPDATE_DATE = DEFAULT, UPDATE_USER = ?  WHERE ID = ? AND CUSTOM_ID = ? AND DELETE_DATE IS NULL ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			pstmt.setInt(3, customId);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
	
	//개인고객의 아이디 찾기
	public String searchId(String email, String hpNo, String name) {
		String id = null;
		try {
			sql = "SELECT ID FROM CUSTOM_PERSON_LOGIN WHERE CUSTOM_ID = (SELECT CUSTOM_ID FROM CUSTOM_PERSON WHERE CUSTOM_NM = ? AND EMAIL = ? AND HP_NO = ? AND DELETE_DATE IS NULL) AND DELETE_DATE IS NULL";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, hpNo);
			rs = pstmt.executeQuery();
			if (rs.next()) id = rs.getString("id");//개인고객의 아이디
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return id;	
	}

	//개인고객의 비밀번호 수정
	public int updatePwd(String id, String juminNo, String shaPwd) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON_LOGIN SET PWD = ?, UPDATE_DATE = DEFAULT, UPDATE_USER = ?  WHERE ID = ? AND CUSTOM_ID = (SELECT CUSTOM_ID FROM CUSTOM_PERSON WHERE JUMIN_NO = ? AND DELETE_DATE IS NULL) AND DELETE_DATE IS NULL";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, shaPwd);
			pstmt.setString(2, id);
			pstmt.setString(3, id);
			pstmt.setString(4, juminNo);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}

	//아이디 중복 체크 / 아이디 존재 체크
	public boolean loginIdCheck(String id) {
		boolean isExist = false;
		try {
			sql = "SELECT ID FROM CUSTOM_PERSON_LOGIN WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) isExist = true;//아이디 존재
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return isExist;
	}
	
	// 로그아웃
	public int updateLogout(String id, int customId) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON_LOGIN SET LOGOUT_DATE = DEFAULT, LOGOUT_USER = ? WHERE ID = ? AND CUSTOM_ID = ? AND DELETE_DATE IS NULL ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			pstmt.setInt(3, customId);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
	
	//개인회원탈퇴(개인고객로그인테이블) - 30일 회원정보유지, 로그인회원삭제(delete_date=탈퇴날짜(회원탈퇴))
	public int updateCustomCompLoginUserDel(String loginId, int customId) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON_LOGIN SET DELETE_DATE = DEFAULT, DELETE_USER = ? , LOGOUT_DATE = DEFAULT, LOGOUT_USER = ?  WHERE ID = ? AND CUSTOM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1 , loginId);
			pstmt.setString(2 , loginId);
			pstmt.setString(3 , loginId);
			pstmt.setInt(4 , customId);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;	
	}
}