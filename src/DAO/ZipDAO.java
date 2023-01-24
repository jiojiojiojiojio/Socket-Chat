package DAO;

import java.util.ArrayList;

import DTO.zipDTO;

public interface ZipDAO {
	ArrayList<zipDTO> searchSido(); // 시.도데이터

	ArrayList<zipDTO> searchGugun(String sido); // 구.군데이터

	ArrayList<zipDTO> searchDong(String sido, String gugun); // 동데이터

	ArrayList<zipDTO> searchAddress(String sido, String gugun, String dong);// 전체주소 조회
}
