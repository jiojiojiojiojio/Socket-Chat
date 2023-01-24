package DAO;

import DTO.InfoDTO;

public interface RegisterDAO {
	int findById(String id); // 회원가입 아이디 중복체크

	int save(InfoDTO infodto); // 회원가입
}
