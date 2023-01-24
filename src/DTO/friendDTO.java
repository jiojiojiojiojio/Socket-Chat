package DTO;

public class friendDTO {

	private String userid; // 사용자 id
	private String fid; // 친구 id
	private String fname; // 친구 이름

	public friendDTO() {
	}

	public friendDTO(String userid, String fid, String fname) {
		super();
		this.userid = userid;
		this.fid = fid;
		this.fname = fname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

}