package DAO;

import java.util.ArrayList;
import java.util.Vector;

import DTO.InfoDTO;
import DTO.friendDTO;

public interface FriendDAO {
	ArrayList<InfoDTO> friendSearch(String Id); // 친구검색

	void inpudb(friendDTO frienddto); // 친구등록 ->friend 테이블 넣기

	Vector<friendDTO> listfriend(String userid); // 친구 리스트 조회

	void friendDel(String id); // 친구 삭제
}
