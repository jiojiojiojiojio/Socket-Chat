package Chatting;

import javax.swing.table.AbstractTableModel;

public class zipTable extends AbstractTableModel {
	// 컬럼의 이름
	String[] columNames = { "우편번호", "시.도", "구.군", "동", "리", "건물명", "번지", "일련번호" };
	// 데이터 공백처리
	Object[][] data = { { " ", " ", " ", " ", " ", " ", " ", " " } };

	public zipTable() {
	}

	public zipTable(Object[][] data) {
		this.data = data;
	}

	// getter 선택한 주소 값으로 변경
	@Override
	public int getColumnCount() {
		return columNames.length;
	}

	@Override
	public int getRowCount() {
		return data.length; // 2차 배열의 길이
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return data[arg0][arg1];
	}

	@Override
	public String getColumnName(int arg0) {
		return columNames[arg0];
	}
}