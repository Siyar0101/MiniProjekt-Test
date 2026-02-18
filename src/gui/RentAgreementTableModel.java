package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.RentalAgreement;

public class RentAgreementTableModel extends AbstractTableModel {
	private static final String[] COL_NAMES = {"Start", "End", "Fee", "Initials", "First name", "Last name", "Spot no."};
	
	private List<RentalAgreement> data;
	

	public RentAgreementTableModel(List<RentalAgreement> data) {
		if(data ==  null) {
			this.data = new ArrayList<>();
		} else {
			this.data = data;
			super.fireTableDataChanged();
		} 
	}
	
	public RentAgreementTableModel() {
		this(null);
	}
	
	
	public void setData(List<RentalAgreement> data) {
		if(data != null) {
			this.data = data;
			super.fireTableDataChanged();
		}
	}
	
	public RentalAgreement getDataAt(int row) {
		RentalAgreement res = null;
		if(row >= 0 && row < data.size()) {
			res = data.get(row);
		}
		return res;
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return COL_NAMES[col];
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		RentalAgreement ra = data.get(rowIndex);
		String res = "---";
		switch (columnIndex) {
		case 0:
			res = ra.getStartDate().toString();
			break;
		case 1:
			res = ra.getEndDate().toString();
			break;
		case 2:
			res = "" + ra.getMonthlyFee();
			break;
		case 3:
			res = ra.getEmployee().getInitials();
			break;
		case 4:
			res = ra.getEmployee().getFirstName();
			break;
		case 5:
			res = ra.getEmployee().getLastName();
			break;
		case 6:
			res = "" + ra.getParkingSpot().getNumber();
			break;
		default:
			break;

		}
		return res;
	}

}
