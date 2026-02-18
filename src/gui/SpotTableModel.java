package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.ParkingSpot;
import model.RentalAgreement;

public class SpotTableModel extends AbstractTableModel {
	private List<ParkingSpot> data;
	private static final String[] COLS = { "Number", "Base Fee" };

	public SpotTableModel() {
		this.data = new ArrayList<>();
	}

	@Override
	public int getColumnCount() {
		return COLS.length;
	}

	@Override
	public String getColumnName(int index) {
		return COLS[index];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ParkingSpot curr = data.get(rowIndex);
		String res = "";
		switch (columnIndex) {
		case 0:
			res = "" + curr.getNumber();
			break;
		case 1:
			res = "" + curr.getBaseMonthlyFee();
			break;
		default:
			res = "UNKNOWN";
			break;
		}
		return res;
	}

	public ParkingSpot getDataAt(int index) {
		ParkingSpot res = null;
		if (index >= 0 && index < data.size()) {
			res = data.get(index);
		}
		return res;

	}

	public void setData(List<ParkingSpot> data) {
		if (data != null) {
			this.data = data;
			super.fireTableDataChanged();
		}
	}

}
