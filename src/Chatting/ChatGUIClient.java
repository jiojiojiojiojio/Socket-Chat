package Chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatGUIClient extends JFrame {

	JTextField txt; // 전송할 텍스트 입력창
	JTextArea chatting; // 전송받은 텍스트 출력
	JButton exit, go, join; // 버튼 변수 선언
	JScrollPane js; // 스크롤바 생성
	JPanel contentPane;

	private static String id; // 사용자 아이디를 저장하는 변수 선언
	private static String ip = "192.168.35.173"; // ip 저장하는 변수
	private static int port = 8080; // 포트번호 저장 변수

	private Socket socket; // 서버와의 통신을 위함
	private InputStream is; // 입력스트림
	private OutputStream os; // 출력스트림
	private DataInputStream dis; // 자료형을 바이트로 입력
	private DataOutputStream dos; // 자료형을 바이트로 출력

	public static void main(String[] args) {

		ChatGUIClient client = new ChatGUIClient(id);
		client.setVisible(true);

	}

	public ChatGUIClient(String id)// 생성자
	{
		this.id = id;

		init();
		start();

		System.out.println("매개 변수로 넘어온 값 : " + id + " " + ip + " " + port + "\n");

		network();

	}

	public void network() {
		// 서버에 접속
		try {
			socket = new Socket(ip, port);
			if (socket != null) // socket이 null값이 아닐때 즉! 연결되었을때
			{
				Connection(); // 연결 메소드를 호출
			}
		} catch (UnknownHostException e) {

		} catch (IOException e) {
			chatting.append("소켓 접속 에러!!\n");
		}

	}

	public void Connection() { // 실직 적인 메소드 연결부분

		try { // 스트림 설정
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

		} catch (IOException e) {
			chatting.append("스트림 설정 에러!!\n");
		}

		send_Message(id); // 정상적으로 연결되면 나의 id를 전송

		Thread th = new Thread(new Runnable() { // 스레드를 돌려서 서버로부터 메세지를 수신

			@Override
			public void run() {
				while (true) {

					try {
						String msg = dis.readUTF(); // 메세지를 수신한다
						chatting.append(msg + "\n");
					} catch (IOException e) {
						chatting.append("메세지 수신 에러!!\n");
						// 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫는다
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							break; // 에러 발생하면 while문 종료
						} catch (IOException e1) {

						}

					}
				} // while문 끝

			}// run메소드 끝
		});

		th.start();

	}

	public void send_Message(String str) { // 서버로 메세지를 보내는 메소드
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
			chatting.append("메세지 송신 에러!!\n");
		}
	}

	public void init() { // 화면구성 메소드

		// 창, 부착할 컴포넌트 생성 및 연결
		txt = new JTextField();
		chatting = new JTextArea();
		JPanel contentPane;

		// 텍스트 출력창에 스크롤 바 연결
		js = new JScrollPane(chatting);
		js.setBounds(10, 50, 430, 540);

		// BorderLayout 배치관리자, JTextArea를 정중앙에 부착
		add(js, "Center");

		// 텍스트 필드를 하단에 부착
		add(txt, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 창 크기 지정
		setBounds(600, 100, 470, 720);

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(new Color(227, 219, 235)); // 프레임 배경색
		contentPane.setLayout(null); // 배치관리자를 임의로 설정

		contentPane.add(js);

		chatting.setCaretPosition(chatting.getDocument().getLength()); // JTextArea에 내용이 추가될 때마다 스크롤 내리지 않고 바로 보기
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // As needed 즉 필요에의해서 내용이 많아지면 스크롤 바가
		// 생긴다
		// 채팅 입력
		txt = new JTextField();
		txt.setBounds(10, 600, 350, 60);
		contentPane.add(txt);
		txt.setColumns(10);

		// 초대하기 버튼
		join = new JButton("초대하기");
		join.setBounds(265, 25, 87, 20);
		join.setBackground(new Color(237, 237, 246));
		contentPane.add(join);

		// 버튼 액션이벤트
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new join(id);
			}

		});

		// 나가기 버튼
		exit = new JButton("나가기");
		exit.setBounds(358, 25, 80, 20);
		exit.setBackground(new Color(237, 237, 246));
		contentPane.add(exit);

		// 버튼 액션이벤트
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					os.close();
					is.close();
					dos.close();
					dis.close();
					socket.close();
					dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		getContentPane().add(exit);

		// 전송 버튼
		go = new JButton("전송");
		go.setBounds(370, 600, 70, 60);
		go.setBackground(new Color(237, 237, 246));
		contentPane.add(go);

		chatting.setLineWrap(true); // 글상자 넘어가면 줄 바꿈
		chatting.setEditable(false); // 입력제한

		// 창이 보이도록 설정
		setVisible(true);

		// 텍스트 필드에 커서 입력
		txt.requestFocus();

		// X버튼 클릭시 정상 종료되도록 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void start() { // 액션이벤트 지정 메소드

		go.addActionListener(new Myaction()); // 내부클래스로 액션 리스너를 상속받은 클래스로
		txt.addActionListener(new Myaction()); // 지정 // 지정

	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == go) // 액션 이벤트가 sendBtn일때
			{

				send_Message(id + " : " + txt.getText());
				txt.setText(""); // 메세지 를 보내고 나면 메세지 쓰는창을 비운다.
				txt.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다

			} else if (e.getSource() == txt) {
				send_Message(txt.getText());
				txt.setText(id + " : " + txt.getText()); // 메세지 를 보내고 나면 메세지 쓰는창을 비운다.
				txt.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
			}
		}
	}
}