package DAO;

public interface LoginDAO {
	int findByIdAndPw(String id, String pwd); // 로그인
}
