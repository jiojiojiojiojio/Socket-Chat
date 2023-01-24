package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import JDBC.DBConnection;

public class LoginDAOImpl implements LoginDAO {
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	// 로그인
	@Override
	public int findByIdAndPw(String id, String pwd) {
		try {
			String sql = "select * from chatting.info where id = ? and pwd = ?";
			pstmt = DBConnection.conn.prepareStatement(sql); // DB쿼리문

			// id와 pwd 전달
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음

			if (rs.next()) { // 값이 있으면 리턴
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
