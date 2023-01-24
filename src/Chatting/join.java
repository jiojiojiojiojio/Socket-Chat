package Chatting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DTO.InfoDTO;
import DTO.friendDTO;

public class join extends JFrame implements ActionListener {
	private JTextField tf_add;
	private DefaultTableModel model;
	private JButton bt_search, bt_join, bt_cancel;
	private JTable table;
	private ChatGUIClient cgc; // 클라이언트 객체 생성
	private static String user_id;

	// user_id 사용
	public join(String user_id) {

		setTitle("채팅방 초대");

		// gui 설계
		JPanel newp = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.drawLine(10, 70, 370, 70);
			}
		};

		newp.setLayout(null);

		// ID라벨
		JLabel la_add = new JLabel("ID");
		la_add.setBounds(35, 10, 60, 60);
		newp.add(la_add);

		// 초대버튼
		bt_join = new JButton("초대");
		bt_join.setBackground(new Color(237, 237, 246));
		bt_join.setBounds(120, 210, 60, 25);
		newp.add(bt_join);

		// 취소버튼
		bt_cancel = new JButton("취소");
		bt_cancel.setBackground(new Color(237, 237, 246));
		bt_cancel.setBounds(195, 210, 60, 25);
		newp.add(bt_cancel);

		// 검색버튼
		bt_search = new JButton("검색");
		bt_search.setBackground(new Color(237, 237, 246));
		bt_search.setBounds(280, 27, 60, 25);
		newp.add(bt_search);

		// 테이블
		String[] col = { "NAME", "ID" };
		String[][] row = new String[0][2];

		// 셀 편집 불가능
		model = new DefaultTableModel(row, col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(40, 90, 300, 100);
		newp.add(scrollPane);

		scrollPane.setViewportView(table);// 테이블에 스크롤페인 추가

		// 검색필드
		tf_add = new JTextField(10);
		tf_add.setBounds(60, 27, 205, 25);
		newp.add(tf_add);

		add(newp);

		setBounds(600, 100, 400, 300);
		setResizable(false);
		setVisible(true);

		// 이벤트 처리
		tf_add.addActionListener(this);
		bt_cancel.addActionListener(this);
		bt_join.addActionListener(this);
	}

	// 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// 검색 버튼
		if (e.getSource() == tf_add) {
			String Id = tf_add.getText();// id를 입력하여 초대할 친구 찾기
			if (Id.length() < 1) { // 입력이 안된 경우
				JOptionPane.showMessageDialog(this, "ID를 입력하세요.");
				return;
			}
			// model의 열 제거
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			InfoDAO dao = new InfoDAO(); // infoDAO 멤버 변수 접근
			ArrayList<InfoDTO> list = null;
			try {
				list = dao.friendSearch(Id); // id로 list안에 있는 정보 찾기
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 찾은 정보(id, name)을 model 테이블에 추가
			for (InfoDTO infodto : list) {
				String[] data = { infodto.getId(), infodto.getName() };
				model.addRow(data);
			}
		}

		// 검색버튼
		else if (e.getSource() == bt_search) {
			String Id = tf_add.getText();
			if (Id.length() < 1) { // 입력이 안된 경우
				JOptionPane.showMessageDialog(this, "ID를 입력하세요.");
				return;
			}
			// model의 열 제거
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			InfoDAO dao = new InfoDAO(); // infoDAO 멤버 변수 접근
			ArrayList<InfoDTO> list = null;
			// Id를 사용하여 InfoDAO에서 DB에 존재하는 친구 찾기
			try {
				list = dao.friendSearch(Id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// InfoDTO에 있는 Id와 Name 정보를 model 테이블에 추가
			for (InfoDTO infodto : list) {
				String[] data = { infodto.getId(), infodto.getName() };
				model.addRow(data);
			}
		}

		// 초대버튼
		if (e.getSource() == bt_join) {
			friendDTO frienddto = new friendDTO(); // friendDTO 멤버 변수 접근
			InfoDAO dao = new InfoDAO(); // infoDAO 멤버 변수 접근

			// 사용자가 입력한 셀을 클릭하면 결과가 열에 출력됨(name, id)
			int row = table.getSelectedRow();
			TableModel model = table.getModel();
			String name = (String) model.getValueAt(row, 0);
			String id = (String) model.getValueAt(row, 1);

			// friendDTO에 존재하는 매개변수 값 호출
			frienddto.setUserid(user_id);
			frienddto.setFid(id);
			frienddto.setFname(name);

			cgc.send_Message(name + "님이 초대되었습니다.");// 클라이언트에 메시지 전송
			JOptionPane.showMessageDialog(null, "초대가 완료되었습니다.");
			dispose();
		}
		// 취소 버튼
		if (e.getSource() == bt_cancel) {
			dispose(); // 하나의 프레임만 종료
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new join(user_id);

	}
}