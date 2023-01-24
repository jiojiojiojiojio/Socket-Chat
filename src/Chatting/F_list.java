package Chatting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DTO.InfoDTO;
import DTO.friendDTO;

public class F_list extends JFrame implements ActionListener {

	private DefaultTableModel model; // 테이블의 변경을 위해 선언
	private JTable table; // 테이블 생성
	private JPanel contentPane; // Panel 선언
	private JLabel txtlist, txtmy, txtfr, txtuser, txtname; // 라벨 변수 선언
	private ImageIcon normalIcon; // 이미지 등록을 위해 변수 선언
	private JButton bt_add, bt_myinfo, bt_del, bt_logout, bt_re; // 버튼 변수 선언

	private String name; // InfoDTO에서 가져온 값 저장 변수 선언
	private String id;
	private static String user_id; // 사용자 아이디를 저장하는 변수 선언

	// 친구 목록
	public F_list(String user_id) {

		setTitle("JH 채팅 프로그램"); // 프레임 타이틀
		setBounds(600, 100, 470, 720); // 프레임 크기

		// 선
		contentPane = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.drawLine(10, 80, 440, 80);
				g.drawLine(10, 102, 440, 102);
				g.drawLine(10, 160, 440, 160);
				g.drawLine(10, 182, 440, 182);
			}
		};
		contentPane.setBackground(new Color(237, 237, 246)); // 프레임 배경색

		contentPane.setLayout(null); // 배치관리자를 임의로 설정

		InfoDAO dao = new InfoDAO(); // InfoDAO클래스 객체화
		InfoDTO infodto = new InfoDTO(); // InfoDTO클래스 객체화
		infodto = dao.select_info(user_id); // select_info()로 user_id 보냄
		name = infodto.getName(); // getName() 값을 name에 저장
		id = infodto.getId();

		// 친구리스트 테이블
		String[] col = { "ID", "NAME" };
		String[][] row = new String[0][2];
		model = new DefaultTableModel(row, col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		}; // 셀 편집 불가능

		// 테이블 생성
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table); // 테이블에 스크롤 생성
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		scrollPane.setBounds(10, 190, 430, 425);
		contentPane.add(scrollPane);

		// 친구등록 실행 시 friendDB에 저장된 값들 테이블 생성
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		Vector<friendDTO> v = dao.listfriend(user_id);

		for (friendDTO frienddtos : v) { // friendSearch()에 ID를 가져와 행에 출력
			String[] data = { frienddtos.getFid(), frienddtos.getFname() };
			model.addRow(data);
		}

		table.setRowHeight(30); // 테이블 셀 크기

		// 친구목록
		txtlist = new JLabel("친구목록");
		txtlist.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		txtlist.setBounds(15, 25, 140, 30);
		contentPane.add(txtlist);

		// 내정보
		txtmy = new JLabel("내정보");
		txtmy.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		txtmy.setBounds(15, 76, 140, 30);
		contentPane.add(txtmy);

		// 친구
		txtfr = new JLabel("친구");
		txtfr.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		txtfr.setBounds(15, 156, 140, 30);
		contentPane.add(txtfr);

		// 아이디
		txtuser = new JLabel(user_id);
		txtuser.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		txtuser.setBounds(15, 120, 90, 25);
		contentPane.add(txtuser);

		// 이름
		txtname = new JLabel(name);
		txtname.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		txtname.setBounds(110, 120, 90, 25);
		contentPane.add(txtname);

		// 친구등록
		normalIcon = new ImageIcon("D:\\혜진\\3학년2학기\\자바2\\add.png");
		bt_add = new JButton(normalIcon);
		bt_add.setBackground(new Color(255, 255, 255));
		bt_add.setBounds(390, 18, 47, 48);
		contentPane.add(bt_add);

		// 새로고침
		bt_re = new JButton("새로고침");
		bt_re.setBackground(new Color(255, 255, 255));
		bt_re.setBounds(352, 163, 88, 17);
		contentPane.add(bt_re);

		// 내정보수정
		bt_myinfo = new JButton("내정보수정");
		bt_myinfo.setBackground(new Color(255, 255, 255));
		bt_myinfo.setBounds(340, 120, 100, 25);
		contentPane.add(bt_myinfo);

		// 삭제
		bt_del = new JButton("삭제");
		bt_del.setBackground(new Color(255, 255, 255));
		bt_del.setBounds(270, 635, 60, 25);
		contentPane.add(bt_del);

		// 로그아웃
		bt_logout = new JButton("로그아웃");
		bt_logout.setBackground(new Color(255, 255, 255));
		bt_logout.setBounds(340, 635, 90, 25);
		contentPane.add(bt_logout);

		add(contentPane);

		// 이벤트 처리
		bt_add.addActionListener(this);
		bt_myinfo.addActionListener(this);
		bt_logout.addActionListener(this);
		bt_re.addActionListener(this);
		bt_del.addActionListener(this);
		table.addMouseListener(new MyMouseAdapter());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료버튼
		setResizable(false); // 크기 조정 불가하도록 설정
		setVisible(true);
		System.out.println(id);
	}

	// 액션이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_add) { // 친구등록 버튼 클릭시
			new newAdd(user_id); // 친구등록 클래스 이동
		} else if (e.getSource() == bt_myinfo) { // 내정보관리 버튼 클릭시
			new MyInfo(id); // 내정보클래스 이동

		} else if (e.getSource() == bt_logout) { // 로그아웃 버튼 클릭 시 로그인화면으로 이동
			JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
			dispose();
			new Login();
		}

		else if (e.getSource() == bt_re) { // 새로고침 버튼 클릭 시 친구 추가
			InfoDAO dao = new InfoDAO();
			Vector<friendDTO> v = dao.listfriend(user_id); // listfriend()에 아이디 넘기고 값들 v에 저장

			// 행이 있으면 제거하고 시작
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}

			for (friendDTO frienddtos : v) { // friendSearch()에 ID를 가져와 행에 출력
				String[] data = { frienddtos.getFid(), frienddtos.getFname() };
				model.addRow(data);
			}
		} else if (e.getSource() == bt_del) { // 취소 버튼 누르면 실행
			InfoDAO dao = new InfoDAO(); // InfoDAO클래스 객체화
			int row = table.getSelectedRow(); // 선택된 행 row에 저장
			TableModel model1 = table.getModel(); // 테이블의 모델을 가져오는 메소드
			if (row < 0)
				return; // 행이 선택되지 않으면(-1) 리턴
			String id = (String) model1.getValueAt(row, 0); // 행의 첫번째 값을 id에 저장
			dao.friendDel(id); // friendDB id 삭제
			model.removeRow(row); // 행 삭제
		}
	}

	// 행 더블 클릭 메소드
	class MyMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) { // 더블클릭

				new ChatGUIClient(name); // 채팅방 연결
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new F_list(user_id);
	}
}