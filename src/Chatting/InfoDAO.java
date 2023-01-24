package Chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import DTO.InfoDTO;
import DTO.friendDTO;
import DTO.zipDTO;

// DB 내용을 담는 클래스
public class InfoDAO {

	static final String DRIVER = "com.mysql.jdbc.Driver"; // JDBC 드라이버 주소
	static final String URL = "jdbc:mysql://localhost:3306/chatting?useSSL=false&autoReconnection=true"; // DB 접속 주소
	static final String USER = "root"; // DB 아이디
	static final String PASS = "123456"; // DB 비밀번호

	private Connection conn; // DB 연결 객체
	private PreparedStatement pstmt; // Query 작성 객체
	private ResultSet rs; // Query 결과 커서

	private static InfoDAO instance = new InfoDAO();

	public static InfoDAO getInstance() {
		return instance;
	}

	// DB연결 메소드
	public Connection getConn() {

		try {
			Class.forName(DRIVER); // 1. 드라이버 로딩
			conn = DriverManager.getConnection(URL, USER, PASS); // 2. 드라이버 연결
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 데이터베이스 연결 종료
	public void disconnection() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		}
	}// ㅈㅣ워버려~

	// 회원가입 아이디 중복체크
	public int findById(String id) {
		conn = getConn(); // DB연결

		try {
			pstmt = conn.prepareStatement("select id from info where id = ?"); // DB쿼리문
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

	// 로그인
	public int findByIdAndPwd(String id, String pwd) {
		conn = getConn(); // DB연결
		try {
			pstmt = conn.prepareStatement("select * from info where id = ? and pwd = ?"); // DB쿼리문

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

	// 회원가입 정보 입력
	public int save(InfoDTO infodto) throws SQLException {

		try {
			conn = getConn(); // DB연결
			String sql = "insert into info(" + "name,id,pwd,birth,gender,addr," + "tel,email,path) "
					+ "values(?,?,?,?,?,?,?,?,?)"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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

	// 친구검색
	public ArrayList<InfoDTO> friendSearch(String Id) throws SQLException {
		ArrayList<InfoDTO> list = new ArrayList<InfoDTO>(); // list 생성

		try {
			conn = getConn(); // DB연결
			String sql = " select name, id from info where id like ? "; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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

	// 친구등록 -> friend 테이블 넣기
	public void inputdb(friendDTO frienddto) {
		try {
			conn = getConn(); // DB연결
			String sql = "insert into friend(userid, friendid, fname)" + "values(?,?,?)"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장

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

	// 친구리스트
	public Vector<friendDTO> listfriend(String userid) {
		Vector<friendDTO> v = new Vector<friendDTO>();

		try {
			conn = getConn(); // DB연결
			String sql = "select * from friend where userid = ?"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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

	// 친구리스트 -> 친구 삭제
	public void friendDel(String id) {
		try {
			conn = getConn(); // DB연결
			String sql = "delete from friend where friendid = ?"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
			pstmt.setString(1, id); // 쿼리문에 값 전달
			pstmt.executeUpdate(); // 반영된 레코드의 건수를 반환

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 내정보보기
	public InfoDTO select_info(String user_id) {
		InfoDTO infodto = new InfoDTO();
		try {
			conn = getConn(); // DB연결
			String sql = " select name, id, pwd, email, path from info where id = ?"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
			pstmt.setString(1, user_id); // 쿼리문에 값 전달
			rs = pstmt.executeQuery(); // rs 객체에 결과값을 담음

			while (rs.next()) {
				// 결과 값을 infodto에 넣음
				infodto.setName(rs.getString(1));
				infodto.setId(rs.getString(2));
				infodto.setPwd(rs.getString(3));
				infodto.setEmail(rs.getString(4));
				infodto.setPath(rs.getString(5));

			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infodto;

	}

	// 내정보수정
	public void updatemyinfo(InfoDTO infodto) {

		try {

			conn = getConn(); // DB연결
			String sql = "update info set pwd = ?, email = ?, path = ?" + "where id=?"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장

			// 결과 값을 infodto에 넣음
			pstmt.setString(1, infodto.getPwd());
			pstmt.setString(2, infodto.getEmail());
			pstmt.setString(3, infodto.getId());
			pstmt.setString(4, infodto.getPath());

			pstmt.executeUpdate(); // 반영된 레코드의 건수를 반환

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 시도데이터
	public ArrayList<zipDTO> searchSido() {
		ArrayList<zipDTO> sidoList = new ArrayList<>();

		try {
			conn = getConn(); // DB연결
			String sql = "select distinct(sido) from zipcode"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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

	// 구군데이터
	public ArrayList<zipDTO> searchGugun(String sido) {
		ArrayList<zipDTO> gugunList = new ArrayList<>();

		try {
			conn = getConn(); // DB연결
			String sql = "select distinct(gugun) from zipcode where sido = \'" + sido + "\' "; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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

	// 동데이터
	public ArrayList<zipDTO> searchDong(String sido, String gugun) {
		ArrayList<zipDTO> dongList = new ArrayList<>();

		try {
			conn = getConn(); // DB연결
			String sql = "select distinct(dong) from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun
					+ "\'"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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

	// 전부주소 데이터
	public ArrayList<zipDTO> searchAddress(String sido, String gugun, String dong) {
		ArrayList<zipDTO> addressList = new ArrayList<>();
		try {
			conn = getConn(); // DB연결
			String sql = "select * from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun
					+ "\' and dong = \'" + dong + "\'"; // DB쿼리문
			pstmt = conn.prepareStatement(sql); // 쿼리문을 저장
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