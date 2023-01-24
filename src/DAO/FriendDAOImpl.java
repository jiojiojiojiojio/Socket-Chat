package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import DTO.InfoDTO;
import DTO.friendDTO;
import JDBC.DBConnection;

public class FriendDAOImpl implements FriendDAO {
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	// 친구 검색
	@Override
	public ArrayList<InfoDTO> friendSearch(String Id) {
		ArrayList<InfoDTO> list = new ArrayList<InfoDTO>(); // list 생성

		try {
			String sql = " select name, id from chatting.info where id like ? "; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			pstmt.setString(1, Id + "%"); // 쿼리문에 값 전달
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음

			while (rs.next()) {
				InfoDTO infodto = new InfoDTO(); // InfoDTO클래스 객체화
				// 결과 값을 infodto에 넣음
				infodto.setId(rs.getString(1));
				infodto.setName(rs.getString(2));
				list.add(infodto);
			}
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 친구 등록 ->friend 테이블 넣기
	@Override
	public void inpudb(friendDTO frienddto) {
		try {
			String sql = "insert into chatting.friend(userid, friendid, fname)" + "values(?,?,?)"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장

			// DB 쿼리문에 값을 넣음
			pstmt.setString(1, frienddto.getUserid());
			pstmt.setString(2, frienddto.getFid());
			pstmt.setString(3, frienddto.getFname());

			pstmt.executeUpdate(); // 반영된 레코드의 건수를 반환
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 친구 리스트 조회
	@Override
	public Vector<friendDTO> listfriend(String userid) {
		Vector<friendDTO> v = new Vector<friendDTO>();

		try {
			String sql = "select * from chatting.friend where userid = ?"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			pstmt.setString(1, userid); // 쿼리문에 값 전달
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음

			while (rs.next()) {
				// 결과 값을 infodto에 넣음
				friendDTO frienddto = new friendDTO();
				frienddto.setUserid(rs.getString(1));
				frienddto.setFid(rs.getString(2));
				frienddto.setFname(rs.getString(3));
				v.add(frienddto);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return v;
	}

	// 친구 삭제
	@Override
	public void friendDel(String id) {
		try {
			String sql = "delete from chatting.friend where friendid = ?"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			pstmt.setString(1, id); // 쿼리문에 값 전달
			pstmt.executeUpdate(); // 반영된 레코드의 건수를 반환

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
