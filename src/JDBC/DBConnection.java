package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection conn = null;
	
	public DBConnection(){};
	
	public boolean DBConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			String url="jdbc:mysql://localhost:3306/chatting?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String user="root";
			String pw="123456";
			
			conn=DriverManager.getConnection(url, user, pw);
			
			System.out.println("연결성공");
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void DisConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
