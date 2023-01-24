package Chatting;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DTO.InfoDTO;

public class Register extends JFrame implements ActionListener {

	private JPanel contentPane; // 버튼을 붙여질 메인 패널 선언
	private Choice tel_list, e_list;
	private String filename = null;
	private ImageIcon imageIcon;
	private JLabel img;

	// 우편번호 검색기에서 검색한 주소 가져오기
	public Register(String address) {
		// gui 설계
		setTitle("회원가입");
		setBounds(600, 100, 780, 700);

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(new Color(227, 219, 235)); // 프레임 배경색
		contentPane.setLayout(null);

		JLabel label = new JLabel("회 원 가 입 ㅎㅅㅎ");
		label.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		label.setBounds(300, 25, 200, 40);
		contentPane.add(label);

		// 이름
		JLabel Name = new JLabel("이 름");
		Name.setBounds(30, 95, 47, 15);
		contentPane.add(Name);

		JTextField txtName = new JTextField();
		txtName.setText(null);
		txtName.setBounds(120, 95, 167, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);

		// 아이디
		JLabel Id = new JLabel("아이디");
		Id.setBounds(30, 145, 60, 20);
		contentPane.add(Id);

		JTextField txtId = new JTextField();
		txtId.setText(null);
		txtId.setBounds(120, 145, 167, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);

		// 아이디 중복체크 버튼
		JButton IdCheckbtn = new JButton("중복확인");
		IdCheckbtn.setBounds(300, 145, 88, 20);
		contentPane.add(IdCheckbtn);
		IdCheckbtn.setBackground(new Color(252, 254, 255));

		// 아이디 중복체크 이벤트 처리
		IdCheckbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoDAO dao = InfoDAO.getInstance(); // infoDAO에 있는 정보를 사용하여 id 중복 비교
				int reault = dao.findById(txtId.getText());
				if (reault == 1) {
					JOptionPane.showMessageDialog(null, "사용중인 아이디 입니다.");
				} else {
					JOptionPane.showMessageDialog(null, "사용가능한 아이디 입니다.");
				}
			}

		});

		// 비밀번호
		JLabel Pw = new JLabel("비밀번호");
		Pw.setBounds(30, 195, 100, 20);
		contentPane.add(Pw);

		// 패스워드 필드를 사용하여 비밀번호 보안
		JPasswordField txtPw = new JPasswordField();
		txtPw.setText(null);
		txtPw.setBounds(120, 195, 167, 21);
		contentPane.add(txtPw);
		txtPw.setColumns(10);

		// 비밀번호 확인
		JLabel PwCheck = new JLabel("비밀번호 확인");
		PwCheck.setBounds(30, 245, 100, 20);
		contentPane.add(PwCheck);

		JPasswordField txtPwCheck = new JPasswordField();
		txtPwCheck.setText(null);
		txtPwCheck.setBounds(120, 245, 167, 21);
		contentPane.add(txtPwCheck);
		txtPwCheck.setColumns(10);

		// 비밀번호 확인 버튼
		JButton PwCheckbtn = new JButton("확인");
		PwCheckbtn.setBounds(300, 245, 60, 20);
		PwCheckbtn.setBackground(new Color(252, 254, 255));
		contentPane.add(PwCheckbtn);

		// 비밀번호 확인 이벤트 처리
		PwCheckbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pw = txtPw.getText().trim(); // pw 문자열 길이
				String pwcheck = txtPwCheck.getText().trim(); // pwcheck 문자열 길이

				// 각 trim 문자열 길이를 비교하여 메시지 출력
				if (pw.length() == 0 || pwcheck.length() == 0) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
				} else if (pw.equals(pwcheck)) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치합니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
				} else if (!pw.equals(pwcheck)) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// 생년월일
		JLabel Birth = new JLabel("생년월일");
		Birth.setBounds(30, 295, 60, 20);
		contentPane.add(Birth);

		// 생년월일에서 년
		JTextField txtYear = new JTextField();
		txtYear.setText(null);
		txtYear.setBounds(120, 295, 60, 21);
		contentPane.add(txtYear);
		txtYear.setColumns(10);

		JLabel Ylabel = new JLabel("년");
		Ylabel.setBounds(185, 295, 60, 20);
		contentPane.add(Ylabel);

		// 생년월일에서 월
		JTextField txtMonth = new JTextField();
		txtMonth.setText(null);
		txtMonth.setBounds(205, 295, 40, 21);
		contentPane.add(txtMonth);
		txtMonth.setColumns(10);

		JLabel Mlabel = new JLabel("월");
		Mlabel.setBounds(250, 295, 40, 20);
		contentPane.add(Mlabel);

		// 생년월일에서 일
		JTextField txtDate = new JTextField();
		txtDate.setText(null);
		txtDate.setBounds(280, 295, 40, 21);
		contentPane.add(txtDate);
		txtDate.setColumns(10);

		JLabel Dlabel = new JLabel("일");
		Dlabel.setBounds(325, 295, 40, 20);
		contentPane.add(Dlabel);

		// 성별 라디오 버튼
		JLabel sex = new JLabel("성별");
		sex.setBounds(30, 345, 60, 20);
		contentPane.add(sex);

		JRadioButton m = new JRadioButton("남", true);
		m.setBounds(120, 345, 60, 20);
		contentPane.add(m);

		JRadioButton fm = new JRadioButton("여", true);
		fm.setBounds(185, 345, 60, 20);

		ButtonGroup g = new ButtonGroup();
		g.add(m);
		g.add(fm);

		this.add(m);
		this.add(fm);

		// 우편번호(주소)
		JLabel Address = new JLabel("우편번호");
		Address.setBounds(30, 395, 60, 20);
		contentPane.add(Address);

		JTextField txtAddress = new JTextField();
		txtAddress.setText(address);
		txtAddress.setBounds(120, 395, 200, 21);
		contentPane.add(txtAddress);

		// 우편번호(주소) 검색 버튼
		JButton Addressbtn = new JButton("검색");
		Addressbtn.setBounds(330, 395, 60, 20);
		contentPane.add(Addressbtn);
		Addressbtn.setBackground(new Color(252, 254, 255));

		// zipSearch 클래스 연결
		Addressbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new zipSearch();

			}
		});

		// 상세주소
		JLabel Address3 = new JLabel("상세주소");
		Address3.setBounds(30, 445, 60, 20);
		contentPane.add(Address3);

		JTextField txtAddress3 = new JTextField();
		txtAddress3.setText(null);
		txtAddress3.setBounds(120, 445, 200, 21);
		contentPane.add(txtAddress3);
		txtAddress.setColumns(10);

		// 연락처
		JLabel phNum = new JLabel("연락처");
		phNum.setBounds(30, 495, 60, 20);
		contentPane.add(phNum);

		tel_list = new Choice();
		tel_list.add("선택");
		tel_list.add("010");
		tel_list.add("011");
		tel_list.add("012");
		tel_list.setBounds(120, 495, 50, 21);
		contentPane.add(tel_list);

		JLabel phNum2 = new JLabel("-");
		phNum2.setBounds(185, 495, 60, 20);
		contentPane.add(phNum2);

		JTextField txtphNum3 = new JTextField();
		txtphNum3.setText(null);
		txtphNum3.setBounds(205, 495, 50, 21);
		contentPane.add(txtphNum3);
		txtphNum3.setColumns(10);

		JLabel phNum3 = new JLabel("-");
		phNum3.setBounds(270, 495, 60, 20);
		contentPane.add(phNum3);

		JTextField txtphNum4 = new JTextField();
		txtphNum4.setText(null);
		txtphNum4.setBounds(290, 495, 50, 21);
		contentPane.add(txtphNum4);
		txtphNum4.setColumns(10);

		// E-mail
		JLabel Email = new JLabel("E-mail");
		Email.setBounds(30, 545, 60, 20);
		contentPane.add(Email);

		JTextField txtEmail = new JTextField();
		txtEmail.setText(null);
		txtEmail.setBounds(120, 545, 70, 21);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel Elabel = new JLabel("@");
		Elabel.setBounds(200, 545, 60, 20);
		contentPane.add(Elabel);

		e_list = new Choice();
		e_list.add("선택");
		e_list.add("naver.com");
		e_list.add("daum.com");
		e_list.add("nate.com");
		e_list.add("google.com");
		e_list.setBounds(235, 545, 110, 20);
		contentPane.add(e_list);

		// 프로필 사진
		JLabel profile = new JLabel("<프로필 사진>");
		profile.setBounds(555, 95, 100, 20);
		contentPane.add(profile);

		imageIcon = new ImageIcon(filename);
		img = new JLabel();
		img.setBounds(515, 135, 160, 160);
		contentPane.add(img);

		// 파일의 경로명을 가져와 프로필 사진 업로드
		JButton pupload = new JButton("파일 선택");
		pupload.addActionListener((ActionListener) this);
		pupload.setBounds(550, 325, 100, 20);
		pupload.setVisible(true);
		contentPane.add(pupload);
		pupload.setBackground(new Color(252, 254, 255));

		// 하단 확인 버튼
		JButton checkbtn = new JButton("확인");
		checkbtn.setBounds(300, 615, 60, 30);
		contentPane.add(checkbtn);
		checkbtn.setBackground(new Color(252, 254, 255));

		// 확인 버튼을 누르면 기입한 회원정보를 infoDTO에 입력
		checkbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String genders = "";
				if (m.isSelected()) {
					genders = "남자";
				} else if (fm.isSelected()) {
					genders = "여자";
				}
				InfoDTO infodto = new InfoDTO();

				infodto.setName(txtName.getText());
				infodto.setId(txtId.getText());
				infodto.setPwd(txtPw.getText());
				infodto.setBirth(txtYear.getText() + "년" + txtMonth.getText() + "월" + txtDate.getText() + "일");
				infodto.setGender(genders);
				infodto.setAddr(txtAddress.getText() + " " + txtAddress3.getText());
				infodto.setTel(tel_list.getSelectedItem() + "-" + txtphNum3.getText() + "-" + txtphNum4.getText());
				infodto.setEmail(txtEmail.getText() + "@" + e_list.getSelectedItem());
				infodto.setPath("C:\\" + filename);

				InfoDAO dao = InfoDAO.getInstance();
				int result = 0;
				try {
					result = dao.save(infodto);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (result == 1) {
					JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
					dispose(); // 하나의 프레임만 종료
				} else {
					JOptionPane.showMessageDialog(null, "정보를 입력해주세요.");
				}
			}
		});

		// 하단 취소 버튼
		JButton cancelbtn = new JButton("취소");
		cancelbtn.setBounds(390, 715, 60, 30);
		contentPane.add(cancelbtn);
		cancelbtn.setBackground(new Color(252, 254, 255));

		cancelbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // 하나의 프레임만 종료
			}
		});

		setResizable(false);
		setVisible(true); // 프레임을 보이게 함

	}

	// 프로필사진 업로드 이벤트 처리
	public void actionPerformed(ActionEvent e) {
		// C경로에서 파일 경로 찾아 이미지 업로드
		JFileChooser jfc = new JFileChooser("C://");
		int sv = jfc.showSaveDialog(this);
		if (sv == 0) {
			filename = jfc.getSelectedFile().getName();
			img.setIcon(imageIcon);
		}
		// 메시지
		if (sv != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "프로필 사진이 업로드 되었습니다.");
			System.out.println(filename);
		}
	}
}
