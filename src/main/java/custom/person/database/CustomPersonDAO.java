package custom.person.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.MysqlConn;

public class CustomPersonDAO {
	private final MysqlConn instance = MysqlConn.getInstance();
	private final Connection conn = instance.getConn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private CustomPersonVO vo = null;
	private String sql = new String("");

	/* --------------------------------------------------
	 * 기업고객아이디 발급
	 * --------------------------------------------------
	 * CUSTOM_ID 구성 : 3자리(100~999) 'CUSTOM_KIND_CD' + 5자리 '순차발행' (00001~99999)
	 * CUSTOM_KIND_CD '1', '2'의 경우는 '100'으로 설정 
	 * --------------------------------------------------
	 */
	public int obtainCustomId(int customKindCd) {
		int customId = 0;
		int idx = 0;
		try {
			sql = "SELECT "
					+ " IFNULL(MAX(CUSTOM_ID) + 1, (CASE WHEN ? = 1 OR ? = 2 THEN 100 ELSE ? END) * 100000 + 1) AS OBT_CUSTOM_ID " 
					+ "FROM CUSTOM_PERSON "
					+ "WHERE SUBSTRING(CUSTOM_ID, 1,3) = CASE WHEN ? = 1 OR ? = 2 THEN 100 ELSE ? END"; 
			    //primary key(custom_id) indexing을 조건절에서 SUBSTRING에 사용하면 조회속도가 높을것 같기도 하는데요? 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++idx, customKindCd);
			pstmt.setInt(++idx, customKindCd);
			pstmt.setInt(++idx, customKindCd);
			pstmt.setInt(++idx, customKindCd);
			pstmt.setInt(++idx, customKindCd);
			pstmt.setInt(++idx, customKindCd);
			rs = pstmt.executeQuery();
			rs.next();//max()는 값 '0'조차 검색해서 가져오므로, 무조건 첫번째 레코드를 읽는다.
			customId = rs.getInt("OBT_CUSTOM_ID");
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return customId;
	}

	//개별회원등록
	public int insert(CustomPersonVO vo) {
		int res = 0, idx = 0;
		try {
			sql = "INSERT INTO CUSTOM_PERSON VALUES ( DEFAULT, ?, ?, 2, ?, 'P', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, ?, NULL, NULL, NULL, NULL )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++idx, vo.getCustomId());
			pstmt.setString(++idx, vo.getCustomNm());
			pstmt.setInt(++idx, vo.getCustomKindCd());
			if (vo.getJob().equals("기타")) { 
				pstmt.setString(++idx , vo.getTxtJob());
			} else {
				pstmt.setString(++idx , vo.getJob());
			}
			pstmt.setString(++idx, vo.getHobby());
			pstmt.setString(++idx, vo.getBirthDate());
			pstmt.setString(++idx, vo.getJuminNo());
			pstmt.setString(++idx, String.valueOf(vo.getGender()));
			pstmt.setString(++idx, vo.getTelNo());
			pstmt.setString(++idx, vo.getHpNo());
			pstmt.setString(++idx, vo.getEmail());
			pstmt.setString(++idx, vo.getPostCode());
			pstmt.setString(++idx, vo.getRoadAddr());
			pstmt.setString(++idx, vo.getExtraAddr());
			pstmt.setString(++idx, vo.getDetailAddr());
			pstmt.setString(++idx, vo.getMemo());
			pstmt.setInt(++idx, vo.getCustomId());//create_user
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
	
	//개별회원정보수정
	public int update(CustomPersonVO vo) {
		int res = 0, idx = 0;
		try {
			sql = "UPDATE CUSTOM_PERSON SET"
					+ " CUSTOM_NM = ?, CUSTOM_KIND_CD = ?, JOB = ? , HOBBY = ? , BIRTH_DATE = ?, JUMIN_NO = ?, "
					+ " GENDER = ?, TEL_NO = ?, HP_NO = ?, EMAIL = ?, POST_CODE = ?, ROAD_ADDR = ?, EXTRA_ADDR = ?, DETAIL_ADDR = ?, "
					+ " MEMO = ?, UPDATE_DATE = DEFAULT, UPDATE_USER = ? "
					+ "WHERE CUSTOM_ID = ? AND DELETE_DATE IS NULL ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++idx, vo.getCustomNm());
			pstmt.setInt(++idx, vo.getCustomKindCd());
			if (vo.getJob().equals("기타")) { 
				pstmt.setString(++idx , vo.getTxtJob());
			} else {
				pstmt.setString(++idx , vo.getJob());
			}
			pstmt.setString(++idx, vo.getHobby());
			pstmt.setString(++idx, vo.getBirthDate());
			pstmt.setString(++idx, vo.getJuminNo());
			pstmt.setString(++idx, String.valueOf(vo.getGender()));
			pstmt.setString(++idx, vo.getTelNo());
			pstmt.setString(++idx, vo.getHpNo());
			pstmt.setString(++idx, vo.getEmail());
			pstmt.setString(++idx, vo.getPostCode());
			pstmt.setString(++idx, vo.getRoadAddr());
			pstmt.setString(++idx, vo.getExtraAddr());
			pstmt.setString(++idx, vo.getDetailAddr());
			pstmt.setString(++idx, vo.getMemo());
			pstmt.setInt(++idx, vo.getCustomId());//udpate_user
			pstmt.setInt(++idx, vo.getCustomId());//primary key
			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
	
	//회원 개별정보 취득
	public CustomPersonVO search(int sCustomId) {
		try {
			sql = "SELECT "
					+ " SEQ," // unique key 
					+ " CUSTOM_ID," // primary key
					+ " CUSTOM_NM ,"
					+ " CUSTOM_KIND_GROUP_CODE,"
					+ " CUSTOM_KIND_CD," //foreign key (코드TBL)
					+ " (SELECT CUSTOM_KIND_NM FROM CUSTOM_KIND WHERE CUSTOM_KIND_CD = THIS.CUSTOM_KIND_CD) AS KIND_NAME," //foreign key (코드TBL)
					+ " CUSTOM_GRADE," //foreign key (코드TBL)
					+ " (SELECT GRADE_NAME FROM CUSTOM_GRADE WHERE GRADE_CODE = THIS.CUSTOM_GRADE) AS GRADE_NAME," //foreign key (코드TBL)
					+ " JOB ,"
					+ " HOBBY ,"
					+ " BIRTH_DATE,"
					+ " JUMIN_NO,"
					+ " GENDER,"
					+ " TEL_NO,"
					+ " HP_NO ,"
					+ " EMAIL ,"
					+ " POST_CODE ,"
					+ " ROAD_ADDR ,"
					+ " EXTRA_ADDR,"
					+ " DETAIL_ADDR ,"
					+ " MEMO,"
					+ " CREATE_DATE ,"
					+ " CREATE_USER ,"
					+ " UPDATE_DATE ,"
					+ " UPDATE_USER ,"
					+ " DELETE_DATE ,"
					+ " DELETE_USER "
					+ "FROM CUSTOM_PERSON AS THIS "
					+ "WHERE CUSTOM_ID = ? AND DELETE_DATE IS NULL "; //primary key(SEQ) indexing
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sCustomId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new CustomPersonVO();
				vo.setSeq(rs.getInt("SEQ"));
				vo.setCustomId(rs.getInt("CUSTOM_ID"));
				vo.setCustomNm(rs.getString("CUSTOM_NM"));
				vo.setCustomKindGroupCode(Integer.parseInt(rs.getString("CUSTOM_KIND_GROUP_CODE")));
				vo.setCustomKindCd(rs.getInt("CUSTOM_KIND_CD"));
				vo.setKindName(rs.getString("KIND_NAME"));
				vo.setCustomGrade(rs.getString("CUSTOM_GRADE").charAt(0));
				vo.setGradeName(rs.getString("GRADE_NAME"));
				vo.setJob(rs.getString("JOB"));
				vo.setHobby(rs.getString("HOBBY"));
				vo.setBirthDate(rs.getString("BIRTH_DATE"));
				vo.setJuminNo(rs.getString("JUMIN_NO"));
				vo.setGender(rs.getString("GENDER").charAt(0));
				vo.setTelNo(rs.getString("TEL_NO"));
				vo.setHpNo(rs.getString("HP_NO"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setPostCode(rs.getString("POST_CODE"));
				vo.setRoadAddr(rs.getString("ROAD_ADDR"));
				vo.setExtraAddr(rs.getString("EXTRA_ADDR"));
				vo.setDetailAddr(rs.getString("DETAIL_ADDR"));
				vo.setMemo(rs.getString("MEMO"));
				vo.setCreateDate(rs.getString("CREATE_DATE"));
				vo.setCreateUser(rs.getString("CREATE_USER"));
				vo.setUpdateDate(rs.getString("UPDATE_DATE"));
				vo.setUpdateUser(rs.getString("UPDATE_USER"));
				vo.setDeleteDate(rs.getString("DELETE_DATE"));
				vo.setDeleteUser(rs.getString("DELETE_USER"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return vo;
	}

	//기업고객의 이메일 도메인 목록 조회
	public String[] searchEmailDomain() {
		String[] emailDomain = null;
		int arrIdx = 0;
		try {
			sql = "SELECT COUNT(DISTINCT SUBSTR(EMAIL, INSTR(EMAIL, '@') + 1, LENGTH(EMAIL))) AS EMAIL_DOMAIN  FROM CUSTOM_PERSON "
					+ "UNION "
					+ "SELECT DISTINCT SUBSTR(EMAIL, INSTR(EMAIL, '@') + 1, LENGTH(EMAIL)) AS EMAIL_DOMAIN  FROM CUSTOM_PERSON ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.first()) emailDomain = new String[rs.getInt("EMAIL_DOMAIN")];
			while (rs.next()) {
				emailDomain[arrIdx] = rs.getString("EMAIL_DOMAIN");
				arrIdx++;
			}
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return emailDomain;
	}
	
	//주민등록번호 중복 체크 - 기존가입여부
	public boolean juminNoCheck(String juminNo) {
		boolean isExist = false;
		try {
			sql = "SELECT JUMIN_NO FROM CUSTOM_PERSON WHERE JUMIN_NO = ? "; //Unique key(jumin_no) indexing
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, juminNo);
			rs = pstmt.executeQuery();
			if (rs.next()) isExist = true;//기존가입 주민등록번호 존재
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return isExist;
	}
	
	//이메일 중복 체크
	public boolean emailCheck(String email) {
		boolean isExist = false;
		try {
			sql = "SELECT EMAIL FROM CUSTOM_PERSON WHERE EMAIL = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) isExist = true;//이메일 존재
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return isExist;
	}
}