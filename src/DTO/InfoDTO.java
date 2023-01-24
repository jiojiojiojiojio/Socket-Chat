package DTO;

// 한명의 회원정보를 저장하는 클래스
public class InfoDTO {
	private String name;
	private String id;
	private String pwd;
	private String birth;
	private String gender;
	private String addr;
	private String tel;
	private String email;
	private String path;

	public InfoDTO() {
	}

	public InfoDTO(String name, String id, String pwd, String birth, String gender, String addr, String tel,
			String email, String path) {
		super();
		this.name = name;
		this.id = id;
		this.pwd = pwd;
		this.birth = birth;
		this.gender = gender;
		this.addr = addr;
		this.tel = tel;
		this.email = email;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}