package Chatting;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DTO.zipDTO;

public class zipSearch extends JFrame implements ActionListener {

	private JPanel contentPane, panel, p;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JButton okbtn, cancelbtn;
	private JScrollPane scrollPane;
	String address1, address2, address3, address4, address5, address6, address7, address8, address;
	InfoDAO infodao = new InfoDAO();

	public static void main(String[] args) {
		new zipSearch();
	}

	public zipSearch() {
		// GUI설계
		setTitle("우편번호 검색");
		dispose();
		setBounds(450, 250, 628, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false); // 프레임 크기 고정(움직일 수 없게 함)
		setVisible(true); // 프레임을 화면에 나타낼 것인지 설정

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "우편번호 검색", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 22, 594, 70);

		contentPane.add(panel);
		panel.setLayout(null);

		okbtn = new JButton("확인");
		okbtn.setBackground(new Color(255, 255, 255));
		okbtn.setBounds(220, 490, 65, 25);
		contentPane.add(okbtn);

		cancelbtn = new JButton("취소");
		cancelbtn.setBackground(new Color(255, 255, 255));
		cancelbtn.setBounds(320, 490, 65, 25);
		contentPane.add(cancelbtn);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 111, 588, 356);
		contentPane.add(scrollPane);

		// 주소를 나타낼 테이블 생성
		table = new JTable();
		table.setModel(new DefaultTableModel(

				new Object[][] { { " ", " ", " ", " ", " ", " ", " ", " " }, }, new String[] { // 일련번호(시리얼 번호)
						"\uC6B0\uD3B8\uBC88\uD638", "\uC2DC.\uB3C4", "\uAD6C.\uAD70", "\uB3D9", "\uB9AC",
						"\uC2DC\uC791\uBC88\uC9C0", "\uB9C8\uC9C0\uB9C9\uBC88\uC9C0", "\uC77C\uB828\uBC88\uD638" }) { // 컬럼은
																														// 편집이
																														// 불가능
																														// 하게
																														// 고정
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

			// 검색한 주소 값을 테이블 행렬에 맞게 집어넣기
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);

		okbtn.addActionListener(this); // 확인 버튼 액션리스너
		cancelbtn.addActionListener(this); // 취소 버튼 액션리스너

		// 첫번째 combobox 생성
		comboBox = new JComboBox();
		comboBox.setBounds(146, 40, 100, 20);
		panel.add(comboBox);
		comboBox.addItem("시.도 선택");

		displaySido();

		// 시.도 콤보박스
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					selectSido(comboBox.getSelectedItem().toString());
			}
		});

		comboBox.setToolTipText(""); // 처음 실행할때 콤보박스 공백으로 설정

		JLabel label = new JLabel("시.도");
		label.setBounds(146, 14, 100, 20);
		panel.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		// 구.군 콤보박스
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(258, 40, 100, 20);
		panel.add(comboBox_1);
		comboBox_1.addItem("구.군 선택");

		JLabel label_1 = new JLabel("구.군");
		label_1.setBounds(258, 14, 100, 20);
		panel.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);

		// 이벤트 리스너 : 사용자가 선택한 주소로 바뀜
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					// table에 집어넣기 실행
					selectGugun(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString());
			}
		});

		// 동 콤보박스
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(370, 40, 100, 20);

		panel.add(comboBox_2);
		comboBox_2.addItem("동 선택");

		JLabel label_2 = new JLabel("동");
		label_2.setBounds(370, 14, 100, 20);
		panel.add(label_2);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);

		// 이벤트 리스너 : 사용자가 선택한 주소로 바뀜
		comboBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// table에 집어넣기 실행
					selectDong(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(),
							comboBox_2.getSelectedItem().toString());
				}
			}
		});
	}

	// 프로그램 시작시 시.도 보여주기
	public void displaySido() {
		ArrayList<zipDTO> sidoList = infodao.searchSido();

		// 시도리스트에서 for문을 돌려 sido만 검색
		for (int i = 0; i < sidoList.size(); i++) {
			zipDTO zipcode = sidoList.get(i);
			comboBox.addItem(zipcode.getSido());
		}
		// DB연결 해제
		infodao.disconnection();
	}

	// sido 선택(구.군 출력)
	public void selectSido(String sido) {

		System.out.println(sido);
		ArrayList<zipDTO> gugunList = infodao.searchGugun(sido);
		comboBox_1.removeAllItems();
		comboBox_2.removeAllItems();
		comboBox_1.addItem("구.군 선택");

		// 구군리스트에서 for문을 돌려 gugun만 검색
		for (int i = 0; i < gugunList.size(); i++) {
			zipDTO zipcode = gugunList.get(i);
			comboBox_1.insertItemAt(zipcode.getGugun(), i);
		}

		table.setModel(new zipTable());
		// DB연결 해제
		infodao.disconnection();
	}

	// gugun 선택(동 출력)
	public void selectGugun(String sido, String gugun) {

		System.out.println(gugun);
		ArrayList<zipDTO> dongList = infodao.searchDong(sido, gugun);
		comboBox_2.removeAllItems();
		comboBox_2.addItem("동 선택");

		// 동리스트에서 for문을 돌려 dong만 검색
		for (int i = 0; i < dongList.size(); i++) {
			zipDTO zipcode = dongList.get(i);
			comboBox_2.insertItemAt(zipcode.getDong(), i);
		}
		table.setModel(new zipTable());
		// DB연결 해제
		infodao.disconnection();
	}

	// Dong 선택(dong을 마지막으로 선택하면 테이블에 전체 주소 출력)
	public void selectDong(String sido, String gugun, String dong) {
		ArrayList<zipDTO> addressList = infodao.searchAddress(sido, gugun, dong);

		Object[][] arrAdd = new Object[addressList.size()][8];
		// 동리스트에서 for문을 돌려 dong만 검색
		for (int i = 0; i < addressList.size(); i++) {
			zipDTO zipcode = addressList.get(i);
			// 테이블에 주소 출력!
			System.out.println(zipcode.getZipcode() + " " + zipcode.getSido() + " " + zipcode.getGugun() + " "
					+ zipcode.getDong() + " " + zipcode.getRi() + " " + zipcode.getBldg() + " " + zipcode.getBunji()
					+ " " + zipcode.getSeq());

			// 테이블에 넣기
			arrAdd[i][0] = zipcode.getZipcode();
			arrAdd[i][1] = zipcode.getSido();
			arrAdd[i][2] = zipcode.getGugun();
			arrAdd[i][3] = zipcode.getDong();
			arrAdd[i][4] = zipcode.getRi();
			arrAdd[i][5] = zipcode.getBldg();
			arrAdd[i][6] = zipcode.getBunji();
			arrAdd[i][7] = zipcode.getSeq();
			table.setModel(new zipTable(arrAdd));
		}

		// DB연결 해제
		infodao.disconnection();
	}

	// 이벤트 리스너
	public void actionPerformed(ActionEvent e) {
		// 확인 버튼을 누르면 회원가입에있는 우편번호 텍스트 필드에 선택한 주소를 나타나게 함
		if (e.getSource() == okbtn) {
			int row = table.getSelectedRow();
			TableModel model = table.getModel();
			address1 = (String) model.getValueAt(row, 0);
			address2 = (String) model.getValueAt(row, 1);
			address3 = (String) model.getValueAt(row, 2);
			address4 = (String) model.getValueAt(row, 3);
			address5 = (String) model.getValueAt(row, 4);
			address6 = (String) model.getValueAt(row, 5);
			address7 = (String) model.getValueAt(row, 6);
			address8 = (String) model.getValueAt(row, 7);

			address = address1 + " " + address2 + " " + address3 + " " + address4 + " " + address5 + " " + address6
					+ " " + address7 + " " + address8;

			Register regi = new Register(address);
			dispose();
		}
		// 취소 버튼 누르면 해당 프레임이 꺼짐
		else if (e.getSource() == cancelbtn) {
			dispose();
		}
	}
}