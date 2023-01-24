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

public class newAdd extends JFrame implements ActionListener {

	private static String user_id; // 사용자 아이디를 저장하는 변수 선언
	private JTextField tf_add; // 아이디 검색 입력 field 변수 선언
	private DefaultTableModel model; // 테이블의 변경을 위해 선언
	private JButton bt_newadd, bt_ok, bt_cancel; // 검색, 등록, 취소 버튼 변수 선언
	private JTable table; // 테이블 변수 선언

	public newAdd(String user_id) {
		setTitle("친구등록"); // 프레임 타이틀

		JPanel newp = new JPanel() {
			public void paint(Graphics g) { // 선
				super.paint(g);
				g.drawLine(10, 70, 370, 70);
			}
		};
		newp.setLayout(null); // 배치관리자를 임의로 설정

		// ID 검색라벨
		JLabel la_add = new JLabel("ID");
		la_add.setBounds(35, 10, 60, 60);
		newp.add(la_add);

		// ID 입력 Field
		tf_add = new JTextField(10);
		tf_add.setBounds(60, 27, 205, 25);
		newp.add(tf_add);

		bt_newadd = new JButton("검색");
		bt_newadd.setBackground(new Color(237, 237, 246));
		bt_newadd.setBounds(280, 27, 60, 25);
		newp.add(bt_newadd);

		// 등록 버튼
		bt_ok = new JButton("등록");
		bt_ok.setBackground(new Color(237, 237, 246));
		bt_ok.setBounds(120, 210, 60, 25);
		newp.add(bt_ok);

		// 취소 버튼
		bt_cancel = new JButton("취소");
		bt_cancel.setBackground(new Color(237, 237, 246));
		bt_cancel.setBounds(195, 210, 60, 25);
		newp.add(bt_cancel);

		// 검색 테이블
		String[] col = { "NAME", "ID" }; // column 값
		String[][] row = new String[0][2];

		model = new DefaultTableModel(row, col) { // 테이블 행 단위, 삭제를 하기 위해 선언
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		}; // 셀 편집 불가능

		table = new JTable(model); // 테이블 생성

		// 테이블 스크롭바 생성
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(40, 90, 300, 100);
		newp.add(scrollPane);

		scrollPane.setViewportView(table);

		add(newp);

		setBounds(600, 100, 400, 300); // 프레임 크기
		setResizable(false); // 크기 편집 불가능
		setVisible(true);

		// 액션 이벤트
		tf_add.addActionListener(this);
		bt_cancel.addActionListener(this);
		bt_ok.addActionListener(this);
		bt_newadd.addActionListener(this);
	}

	// 액션이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf_add) { // 아이디 입력 후 엔터를 누르면 실행
			String Id = tf_add.getText(); // field에 적은 아이디를 변수에 저장
			if (Id.length() == 0) { // 입력이 안된 경우
				JOptionPane.showMessageDialog(this, "ID를 입력하세요.");
				return;
			}

			// 테이블에 행이 있으면 그 수 만큼 for문을 돌려 삭제
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			InfoDAO dao = new InfoDAO(); // InfoDAO클래스 객체화
			ArrayList<InfoDTO> list = null;
			try { // 예외처리
				list = dao.friendSearch(Id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (InfoDTO infodto : list) { // friendSearch()에 ID를 가져와 행에 출력
				String[] data = { infodto.getId(), infodto.getName() };
				model.addRow(data);
			}
		}

		else if (e.getSource() == bt_newadd) { // 검색버튼 누르면 실행
			String Id = tf_add.getText(); // field에 적은 아이디를 변수에 저장
			if (Id.length() == 0) { // 입력이 안된 경우
				JOptionPane.showMessageDialog(this, "ID를 입력하세요.");
				return;
			}
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			InfoDAO dao = new InfoDAO(); // InfoDAO클래스 객체화
			ArrayList<InfoDTO> list = null;
			try {
				list = dao.friendSearch(Id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (InfoDTO infodto : list) { // friendSearch()에 ID를 가져와 행에 출력
				String[] data = { infodto.getId(), infodto.getName() };
				model.addRow(data);
			}
		}

		else if (e.getSource() == bt_ok) { // 등록버튼 누르면 실행
			friendDTO frienddto = new friendDTO(); // friendDTO클래스 객체화
			InfoDAO dao = new InfoDAO(); // InfoDAO클래스 객체화

			int row = table.getSelectedRow(); // 선택된 행을 row에 저장
			TableModel model = table.getModel(); // 테이블의 모델을 가져오는 메소드
			String name = (String) model.getValueAt(row, 0); // 모델의 첫번째 행 값을 name에 저장
			String id = (String) model.getValueAt(row, 1); // 모델의 두번째 행 값을 id에 저장

			frienddto.setUserid(user_id); // friendDTO클래스에 값 보냄
			frienddto.setFid(id); // friendDTO클래스에 값 보냄
			frienddto.setFname(name); // friendDTO클래스에 값 보냄

			dao.inputdb(frienddto); // inputdb()에 저장된 값 전달
			JOptionPane.showMessageDialog(null, "친구등록이 완료되었습니다.");
			dispose();
		}

		else if (e.getSource() == bt_cancel) { // 최소버튼 누르면 실행
			dispose(); // 하나의 프레임만 종료
		}
	}
}