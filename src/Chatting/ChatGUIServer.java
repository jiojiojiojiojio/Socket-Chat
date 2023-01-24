package Chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;

public class ChatGUIServer extends JFrame {

	private ServerSocket socket; // 서버소켓
	private Socket soc; // 연결소켓
	private int Port = 8080; // 포트번호

	private static Vector vc = new Vector(); // 연결된 사용자를 저장할 벡터

	public ChatGUIServer() {

		server_start();

	}

	// 서버 시작 메소드
	private void server_start() {

		try {
			socket = new ServerSocket(Port); // 서버가 포트 여는부분
			System.out.println("socket>>>" + socket);
			System.out.println("채팅 서버 가동중...");

			if (socket != null) // socket 이 정상적으로 열렸을때
			{
				Connection();
			}

		} catch (IOException e) {
			System.out.println("소켓이 이미 사용중입니다...");

		}

	}

	// 클라이언트와 연결 메소드
	private void Connection() {

		Thread th = new Thread(new Runnable() { // 사용자 접속을 받을 스레드

			@Override
			public void run() {
				while (true) { // 사용자 접속을 계속해서 받기 위해 while문
					try {
						System.out.println("사용자 접속 대기중...");
						soc = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
						System.out.println("사용자 접속!!");

						UserInfo user = new UserInfo(soc, vc); // 연결된 소켓 정보는 금방 사라지므로, user 클래스 형태로 객체 생성
						// 매개변수로 현재 연결된 소켓과, 벡터를 담아둔다
						vc.add(user); // 해당 벡터에 사용자 객체를 추가

						user.start(); // 만든 객체의 스레드 실행

					} catch (IOException e) { // 접속에러
						System.out.println("!!!! accept 에러 발생... !!!!");
					}

				}

			}
		});

		th.start();

	}

	static class UserInfo extends Thread {

		private InputStream is; // 입력스트림
		private OutputStream os; // 출력스트림
		private DataInputStream dis; // 자료형을 바이트로 입력
		private DataOutputStream dos; // 자료형을 바이트로 출력

		private Socket user_socket;
		private static Vector user_vc; // 사용자를 담음

		private static String Nickname = "";

		public UserInfo(Socket soc, Vector vc) // 생성자메소드
		{
			// 매개변수로 넘어온 자료 저장
			this.user_socket = soc;
			this.user_vc = vc;

			User_network();

		}

		public void User_network() {
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);

				Nickname = dis.readUTF(); // 사용자의 닉네임 받는부분
				System.out.println("접속자 ID " + Nickname + "");

			} catch (Exception e) {
				System.out.println("스트림 셋팅 에러");
			}

		}

		// 클라이언트가 보낸 메세지 받는 메소드
		public void InMessage(String str) {
			System.out.println("사용자로부터 들어온 메세지 : " + str + "");
			// 사용자 메세지 처리
			broad_cast(str); // broad_cast()로 메세지를 보냄

		}

		// 받은 메세지를 클라이언트에게 보여줌
		public static void broad_cast(String str) {
			for (int i = 0; i < user_vc.size(); i++) { // 클라이언트 수만큼 for문 돌림
				UserInfo imsi = (UserInfo) user_vc.elementAt(i);
				imsi.send_Message(str); // send_Message()에 메세지 보냄
			}

		}

		// 클라이언트 화면에 출력
		public void send_Message(String str) {
			try {
				dos.writeUTF(str); // 출력
			} catch (IOException e) {
				System.out.println("메시지 송신 에러 발생");
			}
		}

		public void run() // 스레드 정의
		{

			UserInfo.broad_cast("[" + Nickname + "]" + "님이 입장했습니다.");
			while (true) {
				try {

					// 사용자에게 받는 메세지
					String msg = dis.readUTF();
					InMessage(msg);

				} catch (IOException e) {

					try {
						dos.close();
						dis.close();
						user_socket.close();
						vc.removeElement(this); // 에러가난 현재 객체를 벡터에서 지운다
						System.out.println(vc.size() + " : 현재 벡터에 담겨진 사용자 수");
						System.out.println("사용자 접속 끊어짐 자원 반납");
						UserInfo.broad_cast("[" + Nickname + "]" + "님이 퇴장했습니다.");
						break;

					} catch (Exception ee) {

					} // catch문 끝
				} // 바깥 catch문끝
			}

		}// run메소드 끝

	} // 내부 userinfo클래스끝

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatGUIServer();
		// new ChatGUIServer(); // 로그인 창 실행시 서버도 같이 실행

	}
}