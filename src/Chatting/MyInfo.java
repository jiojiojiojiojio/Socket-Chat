package Chatting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DTO.InfoDTO;

public class MyInfo extends JFrame implements ActionListener {
	InfoDTO infodto = new InfoDTO();
	InfoDAO dao = new InfoDAO();
	private String user_id;
	private String ID;

	private JTextField txtEmail;
	private JPasswordField txtPw, txtPwCheck;
	private JButton PwCheckbtn, checkbtn, cancelbtn, pupload;
	private String filename = null;
	private ImageIcon imageIcon, imageIcon1;
	private JLabel img;

	// 입력된 id 값을 가져옴
	public MyInfo(String user_id) {

		this.user_id = user_id;

		ID = user_id; // 받아온 아아디를 변수 ID에 다시 저장

		setTitle("내 정보 관리");
		setBounds(600, 100, 420, 620);

		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(new Color(227, 219, 235)); // 프레임 배경색
		contentPane.setLayout(null);

		// info에 있는 name, Id, pwd, email, filename 정보를 가져옴
		infodto = dao.select_info(user_id);
		String name = infodto.getName();
		String pwd = infodto.getPwd();
		String email = infodto.getEmail();
		String filename1 = infodto.getPath();

		// gui 설계
		JLabel label = new JLabel("내 정보 관리:)");
		label.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		label.setBounds(140, 25, 200, 20);
		contentPane.add(label);

		// 이름
		JLabel Name = new JLabel("이 름");
		Name.setBounds(30, 95, 47, 15);
		contentPane.add(Name);

		JLabel txtName = new JLabel(name);
		txtName.setBounds(130, 95, 130, 21);
		contentPane.add(txtName);

		// 아이디
		JLabel Id = new JLabel("아이디");
		Id.setBounds(30, 145, 60, 20);
		contentPane.add(Id);

		JLabel txtId = new JLabel(user_id);
		txtId.setBounds(130, 145, 130, 21);
		contentPane.add(txtId);

		// 비밀번호
		JLabel Pw = new JLabel("비밀번호 재설정");
		Pw.setBounds(30, 195, 100, 20);
		contentPane.add(Pw);

		// 비밀번호 변경이 가능하게 패스워드 편집을 가능하게 함
		txtPw = new JPasswordField(pwd);
		txtPw.setBounds(130, 195, 167, 21);
		contentPane.add(txtPw);
		txtPw.setColumns(10);

		// 비밀번호 확인
		JLabel PwCheck = new JLabel("비밀번호 확인");
		PwCheck.setBounds(30, 245, 100, 20);
		contentPane.add(PwCheck);

		txtPwCheck = new JPasswordField();
		txtPwCheck.setText("");
		txtPwCheck.setBounds(130, 245, 167, 21);
		contentPane.add(txtPwCheck);
		txtPwCheck.setColumns(10);

		// 비밀번호 확인 버튼
		PwCheckbtn = new JButton("확인");
		PwCheckbtn.setBounds(300, 245, 60, 20);
		PwCheckbtn.setBackground(new Color(252, 254, 255));
		contentPane.add(PwCheckbtn);

		// E-mail
		JLabel Email = new JLabel("E-mail");
		Email.setBounds(30, 295, 60, 20);
		contentPane.add(Email);

		// 이메일 변경이 가능하게 함
		txtEmail = new JTextField(email);
		txtEmail.setBounds(130, 295, 150, 21);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		// 프로필 사진
		JLabel profile = new JLabel("<프로필 사진>");
		profile.setBounds(50, 345, 100, 20);
		contentPane.add(profile);

		// 프로필 사진 변경이 가능하게 함
		imageIcon = new ImageIcon(filename1);
		Image img1 = imageIcon.getImage(); // 이미지 파일을 불러옴
		Image img2 = img1.getScaledInstance(120, 100, java.awt.Image.SCALE_SMOOTH); // 크기에 맞게 이미지 고정
		imageIcon1 = new ImageIcon(img2);
		img = new JLabel(imageIcon1);
		img.setBounds(30, 370, 120, 100);
		contentPane.add(img);

		pupload = new JButton("파일 업로드");
		pupload.setBounds(170, 450, 105, 20);
		contentPane.add(pupload);
		pupload.setBackground(new Color(252, 254, 255));
		pupload.addActionListener(this);

		// 하단 확인 버튼
		checkbtn = new JButton("확인");
		checkbtn.setBounds(140, 530, 60, 30);
		contentPane.add(checkbtn);
		checkbtn.setBackground(new Color(252, 254, 255));

		// 하단 취소 버튼
		cancelbtn = new JButton("취소");
		cancelbtn.setBounds(210, 530, 60, 30);
		contentPane.add(cancelbtn);
		cancelbtn.setBackground(new Color(252, 254, 255));

		// 버튼 액션 이벤트
		checkbtn.addActionListener(this);
		PwCheckbtn.addActionListener(this);
		cancelbtn.addActionListener(this);

		setResizable(false);
		setVisible(true); // 프레임 보이게하는거
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 비밀번호 중복 확인 버튼
		if (e.getSource() == PwCheckbtn) {

			String pw = txtPw.getText().trim();
			String pwcheck = txtPwCheck.getText().trim();

			// 비밀번호 중복 확인 메세지
			if (pw.length() == 0 || pwcheck.length() == 0) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
			} else if (pw.equals(pwcheck)) {
				JOptionPane.showMessageDialog(null, "비밀번호가 일치합니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			} else if (!pw.equals(pwcheck)) {
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		// 내 정보 수정 확인 버튼
		else if (e.getSource() == checkbtn) { // 안에 if문 해서 제이옵션 처리해야함

			infodto.setId(ID);
			infodto.setPwd(txtPw.getText());
			infodto.setEmail(txtEmail.getText());
			infodto.setPath("C:\\" + filename);
			System.out.println(infodto.getPath());

			dao.updatemyinfo(infodto);
			JOptionPane.showMessageDialog(null, "정보수정이 완료되었습니다.");
			dispose();
		}
		if (e.getSource() == cancelbtn) { // 취소버튼을 누르면 해당 프레임이 닫힘
			dispose();
		}
		// 프로필 사진 업로드 버튼
		else if (e.getSource() == pupload) {
			JFileChooser jfc = new JFileChooser("C://"); // 경로명 C에서 JFileChooser 열기
			// 프로필 사진 가져오기
			int sv = jfc.showSaveDialog(this);
			if (sv == 0) {
				filename = jfc.getSelectedFile().getName();
				img.setIcon(imageIcon);
			}
			// 프로필 사진 업로드 메시지
			if (sv != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "프로필 사진이 업로드 되었습니다.");
				System.out.println(filename);
			}
		}
	}
}
