package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.zipDTO;
import JDBC.DBConnection;

public class ZipDAOImpl implements ZipDAO {
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	// 시.도 데이터
	@Override
	public ArrayList<zipDTO> searchSido() {
		ArrayList<zipDTO> sidoList = new ArrayList<>();

		try {
			String sql = "select distinct(sido) from chatting.zipcode"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음
			while (rs.next()) {
				// 결과 값을 zipcode에 넣음
				zipDTO zipcode = new zipDTO();
				zipcode.setSido(rs.getString("SIDO"));
				sidoList.add(zipcode);
			}
		} catch (SQLException e) {
		}

		return sidoList;
	}

	// 구.군 데이터
	@Override
	public ArrayList<zipDTO> searchGugun(String sido) {
		ArrayList<zipDTO> gugunList = new ArrayList<>();

		try {
			String sql = "select distinct(gugun) from chatting.zipcode where sido = \'" + sido + "\' "; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음
			while (rs.next()) {
				// 결과 값을 zipcode에 넣음
				zipDTO zipcode = new zipDTO();
				zipcode.setGugun(rs.getString("GUGUN"));
				gugunList.add(zipcode);
			}

		} catch (SQLException e) {

		}

		return gugunList;
	}

	// 동 데이터
	@Override
	public ArrayList<zipDTO> searchDong(String sido, String gugun) {
		ArrayList<zipDTO> dongList = new ArrayList<>();

		try {
			String sql = "select distinct(dong) from chatting.zipcode where sido = \'" + sido + "\'  and gugun = \'"
					+ gugun + "\'"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음
			while (rs.next()) { // 결과 값을 zipcode에 넣음
				zipDTO zipcode = new zipDTO();
				zipcode.setDong(rs.getString("DONG"));
				dongList.add(zipcode);
			}
		} catch (SQLException e) {

		}
		return dongList;
	}

	// 전체주소 조회
	@Override
	public ArrayList<zipDTO> searchAddress(String sido, String gugun, String dong) {
		ArrayList<zipDTO> addressList = new ArrayList<>();
		try {
			String sql = "select * from chatting.zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun
					+ "\' and dong = \'" + dong + "\'"; // DB쿼리문
			pstmt = DBConnection.conn.prepareStatement(sql); // 쿼리문을 저장
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음
			while (rs.next()) { // 결과 값을 zipcode에 넣음
				zipDTO zipcode = new zipDTO();
				zipcode.setZipcode(rs.getString("zipcode"));
				zipcode.setSido(rs.getString("sido"));
				zipcode.setGugun(rs.getString("gugun"));
				zipcode.setDong(rs.getString("dong"));
				zipcode.setRi(rs.getString("ri"));
				zipcode.setBldg(rs.getString("bldg"));
				zipcode.setBunji(rs.getString("bunji"));
				zipcode.setSeq(rs.getString("seq"));
				addressList.add(zipcode);
			}
		} catch (SQLException e) {
		}
		return addressList;
	}

}
