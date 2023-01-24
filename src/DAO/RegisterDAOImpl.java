package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DTO.InfoDTO;
import JDBC.DBConnection;

public class RegisterDAOImpl implements RegisterDAO {
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	// 회원가입 아이디 중복체크
	@Override
	public int findById(String id) {
		try {
			String sql = "select id from chatting.info where (id = ?)";
			pstmt = DBConnection.conn.prepareStatement(sql);
			pstmt.setString(1, id); // 쿼리문에 값 전달
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음

			if (rs.next()) { // 값이 있으면 리턴
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// 회원가입
	@Override
	public int save(InfoDTO infodto) {
		try {
			String sql = "insert into chatting.info(" + "name,id,pwd,birth,gender,addr," + "tel,email,path) "
					+ "values(?,?,?,?,?,?,?,?,?)"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			// DB 쿼리문에 값을 넣음
			pstmt.setString(1, infodto.getName());
			pstmt.setString(2, infodto.getId());
			pstmt.setString(3, infodto.getPwd());
			pstmt.setString(4, infodto.getBirth());
			pstmt.setString(5, infodto.getGender());
			pstmt.setString(6, infodto.getAddr());
			pstmt.setString(7, infodto.getTel());
			pstmt.setString(8, infodto.getEmail());
			pstmt.setString(9, infodto.getPath());
			pstmt.executeUpdate(); // 반영된 레코드의 건수를 반환
			pstmt.close();

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
