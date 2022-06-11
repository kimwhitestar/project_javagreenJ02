package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlConn {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = new String("com.mysql.jdbc.Driver");
	private String url = new String("jdbc:mysql://localhost:3306/javagreen_khv");
	private String user = new String("root");
	private String password = new String("1234");
	
	private static MysqlConn instance = new MysqlConn(); //singleton unique 객체 생성
	
	//singleton을 위한 생성자 비공개 
	//다른 사용자 new생성 못함
	private MysqlConn() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패~~");
		} catch (SQLException e) {
			System.out.println("데이타베이스 연결 실패~~");
		}
	}

	//만들어진 비공개 singleton 가져가기
	public static MysqlConn getInstance() {
		return instance;
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public void pstmtClose() {
		if (null != pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {e.getMessage();}
		}
	}
	public void rsClose() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {e.getMessage();}
		}
	}
}
