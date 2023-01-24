package Chatting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	/*
	 * 로그인
	 * 로그인 -> 회원가입 
	 * 로그인 -> 친구목록
	 */

	static String user_id; // 사용자 아이디를 받을 변수 선언
	private JPanel contentPane; // 버튼을 붙여질 메인 패널 선언
	private JLabel id, pw; // id, pw 라벨 선언
	private JTextField txtId; // id 입력창 field 선언
	private JPasswordField txtpw; // pw 입력창 보안을 위해 선언
	private JButton loginbtn, register; // 로그인버튼, 회원가입 버튼 선언
	private ImageIcon icon; // 배경화면 이미지 아이콘 변수 선언

	public Login() {
		setTitle("JH 채팅 프로그램"); // 프레임 타이틀
		setBounds(600, 100, 420, 620); // 프레임 크기 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 프로세스까지 깔끔히 종료

		icon = new ImageIcon("로고7.jpg"); // 배경화면 이미지 설정
		contentPane = new JPanel() {
			public void paintComponent(Graphics g) { // 컴포넌트가 자신의 모양을 그림
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ID라벨
		id = new JLabel("I D");
		id.setBounds(60, 360, 47, 15);
		contentPane.add(id);

		// ID입력창
		txtId = new JTextField(10);
		txtId.setText("");
		txtId.setBounds(100, 360, 167, 21);
		contentPane.add(txtId);
		txtId.setColumns(10); // 10자까지 입력제한

		// 패스워드
		pw = new JLabel("PW");
		pw.setBounds(60, 400, 47, 15);
		contentPane.add(pw);

		// 패스워드 입력창
		txtpw = new JPasswordField(10);
		txtpw.setText("");
		txtpw.setBounds(100, 400, 167, 21);
		contentPane.add(txtpw);
		txtpw.setColumns(10); // 10자까지 입력제한

		// 로그인 버튼
		loginbtn = new JButton("로그인");
		loginbtn.setBounds(280, 360, 80, 60);
		contentPane.add(loginbtn);
		loginbtn.setBackground(new Color(255, 255, 255)); // 버튼 색 설정

		// 로그인->회원가입 버튼
		setContentPane(contentPane);
		register = new JButton("회원가입");
		register.setBounds(140, 439, 90, 15);
		register.setBorderPainted(false); // JButton의 Border을 없애줌
		register.setContentAreaFilled(false); // JButton의 내용영역 채우기 안함
		register.setFocusPainted(false); // JButton이 선택되었을 때 생기는 테두리 사용안함
		contentPane.add(register);

		// 액션이벤트 처리
		loginbtn.addActionListener(this);
		register.addActionListener(this);

		setResizable(false); // 프레임 크기 조정 못하게 함
		setVisible(true);
	}

	// 액션이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginbtn) { // 로그인 버튼 액션 이벤트
			String id = txtId.getText(); // 입력받은 id를 id 변수에 저장
			String pwd = txtpw.getText(); // 입력받은 pw를 pwd 변수에 저장

//			InfoDAO2 dao = new InfoDAO2();	// InfoDAO클래스 객체화
//			int result = dao.findByIdAndPwd(id, pwd);	// id와 pwd를 InfoDAO findByIdAndPwd()에 보냄

			InfoDAO dao = new InfoDAO(); // InfoDAO클래스 객체화
			int result = dao.findByIdAndPwd(id, pwd); // id와 pwd를 InfoDAO findByIdAndPwd()에 보냄
			if (result == 1) { // result가 1, 즉 안에 정보가 있으면 실행

				JOptionPane.showMessageDialog(null, "로그인 성공"); // 팝업창

				user_id = txtId.getText(); // 로그인한 아이디를 user_id로 받음
				new F_list(user_id); // 친구목록 창으로 넘어감
				dispose();
			} else { // 정보가 없으면 실패
				JOptionPane.showMessageDialog(null, "로그인 실패"); // 팝업창
			}
		}

		else if (e.getSource() == register) { // 회원가입 버튼 액션 이벤트
			new Register(null); // 회원가입 창으로 넘어감
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();
		new ChatGUIServer(); // 로그인 창 실행시 서버도 같이 실행

	}

	public String getUser_id() {
		return user_id;
	}
}