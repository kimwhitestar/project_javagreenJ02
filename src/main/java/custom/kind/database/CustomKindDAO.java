package custom.kind.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conn.MysqlConn;

public class CustomKindDAO {
	private final MysqlConn instance = MysqlConn.getInstance();
	private final Connection conn = instance.getConn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private CustomKindVO vo = null;
	private String sql = new String("");

	//기업고객고분코드 목록조회 
	public List<CustomKindVO> searchCustomCompCustomKindList() {
		List<CustomKindVO> vos = new ArrayList<>();
		CustomKindVO vo = null;
		try {
			sql = "SELECT "
					+ " CUSTOM_KIND_CD," // primary key
					+ " CUSTOM_KIND_NM " 
					+ "FROM CUSTOM_KIND "
					+ "WHERE CUSTOM_KIND_CD > 2 " //기업고객고분코드 제외 조건
					+ "ORDER BY CUSTOM_KIND_CD"; //ASC생략시 DEFAULT로 ASENDING됨
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new CustomKindVO();
				vo.setCustomKindCd(rs.getInt("CUSTOM_KIND_CD"));
				vo.setCustomKindNm(rs.getString("CUSTOM_KIND_NM"));
				vos.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return vos;
	}
	
	//개인고객고분코드 목록조회 
	public List<CustomKindVO> searchCustomPersonCustomKindList() {
		List<CustomKindVO> vos = new ArrayList<>();
		CustomKindVO vo = null;
		try {
			sql = "SELECT "
					+ " CUSTOM_KIND_CD," // primary key
					+ " CUSTOM_KIND_NM " 
					+ "FROM CUSTOM_KIND "
					+ "WHERE CUSTOM_KIND_CD NOT IN (1, 2, 203, 501, 502, 503, 506) " //개인고객고분코드 제외 조건
					+ "ORDER BY CUSTOM_KIND_CD"; //ASC생략시 DEFAULT로 ASENDING됨
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new CustomKindVO();
				vo.setCustomKindCd(rs.getInt("CUSTOM_KIND_CD"));
				vo.setCustomKindNm(rs.getString("CUSTOM_KIND_NM"));
				vos.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return vos;
	}
}