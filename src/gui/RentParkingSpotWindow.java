package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.RentController;
import db.DataAccessException;
import model.Employee;
import model.ParkingSpot;
import model.Vehicle;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;

public class RentParkingSpotWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmployeeInitials;
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnFindEmployee;
	private JTextPane txtEmployeeInfo;
	private JButton btnSearchSpot;
	private JTable tblSpots;
	private RentController rc;
	private SpotTableModel stm;
	private UCNDatePicker dpFrom;
	private UCNDatePicker dpTo;

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 */
	public RentParkingSpotWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 501, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("Employee initials: ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		txtEmployeeInitials = new JTextField();
		txtEmployeeInitials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findEmployeeClicked();
			}
		});
		GridBagConstraints gbc_txtEmployeeInitials = new GridBagConstraints();
		gbc_txtEmployeeInitials.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmployeeInitials.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmployeeInitials.gridx = 1;
		gbc_txtEmployeeInitials.gridy = 0;
		panel.add(txtEmployeeInitials, gbc_txtEmployeeInitials);
		txtEmployeeInitials.setColumns(10);

		btnFindEmployee = new JButton("Find");
		btnFindEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findEmployeeClicked();
			}
		});
		GridBagConstraints gbc_btnFindEmployee = new GridBagConstraints();
		gbc_btnFindEmployee.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindEmployee.gridx = 2;
		gbc_btnFindEmployee.gridy = 0;
		panel.add(btnFindEmployee, gbc_btnFindEmployee);

		txtEmployeeInfo = new JTextPane();
		txtEmployeeInfo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtEmployeeInfo.setBackground(SystemColor.control);
		txtEmployeeInfo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtEmployeeInfo.setDisabledTextColor(Color.BLACK);
		txtEmployeeInfo.setPreferredSize(new Dimension(7, 40));
		txtEmployeeInfo.setEnabled(false);
		txtEmployeeInfo.setEditable(false);
		GridBagConstraints gbc_txtEmployeeInfo = new GridBagConstraints();
		gbc_txtEmployeeInfo.gridheight = 2;
		gbc_txtEmployeeInfo.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmployeeInfo.gridwidth = 2;
		gbc_txtEmployeeInfo.fill = GridBagConstraints.BOTH;
		gbc_txtEmployeeInfo.gridx = 1;
		gbc_txtEmployeeInfo.gridy = 1;
		panel.add(txtEmployeeInfo, gbc_txtEmployeeInfo);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});
		panel_1.add(btnCancel);

		btnOK = new JButton("Save");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveClicked();
			}
		});
		panel_1.add(btnOK);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JLabel lblNewLabel_1 = new JLabel("From: ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);

		dpFrom = new UCNDatePicker();
		GridBagConstraints gbc_dpFrom = new GridBagConstraints();
		gbc_dpFrom.insets = new Insets(0, 0, 0, 5);
		gbc_dpFrom.fill = GridBagConstraints.BOTH;
		gbc_dpFrom.gridx = 1;
		gbc_dpFrom.gridy = 0;
		panel_3.add(dpFrom, gbc_dpFrom);

		JLabel lblNewLabel_2 = new JLabel("        To: ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 0;
		panel_3.add(lblNewLabel_2, gbc_lblNewLabel_2);

		btnSearchSpot = new JButton("Find available");
		btnSearchSpot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findAvailableClicked();
			}
		});

		dpTo = new UCNDatePicker();
		GridBagConstraints gbc_dpTo = new GridBagConstraints();
		gbc_dpTo.insets = new Insets(0, 0, 0, 5);
		gbc_dpTo.fill = GridBagConstraints.BOTH;
		gbc_dpTo.gridx = 3;
		gbc_dpTo.gridy = 0;
		panel_3.add(dpTo, gbc_dpTo);
		GridBagConstraints gbc_btnSearchSpot = new GridBagConstraints();
		gbc_btnSearchSpot.gridx = 4;
		gbc_btnSearchSpot.gridy = 0;
		panel_3.add(btnSearchSpot, gbc_btnSearchSpot);

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		tblSpots = new JTable();
		scrollPane.setViewportView(tblSpots);

		init();
	}

	private void init() {
		try {
			rc = new RentController();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "No worky DB " + e.getMessage());
			e.printStackTrace();
			System.exit(ABORT);
		}
		tblSpots.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (tblSpots.getSelectedRow() > -1) {
						enable(3);
					}
				}
			}
		});
		this.dpTo.after(dpFrom);
		enable(1);
	}

	private void findEmployeeClicked() {
		String inits = txtEmployeeInitials.getText();
		try {
			Employee e = rc.enterEmployeeInitials(inits);
			if (e == null) {
				JOptionPane.showMessageDialog(this, "Can't find employee with initials " + inits);
			} else {
				displayEmployee(e);
			}

		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Something went wrong searching for the employee " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void displayEmployee(Employee e) {
		enable(1);
		String empinfo = "";

		if (e != null) {
			empinfo += e.getFirstName() + " " + e.getLastName() + " (" + e.getInitials() + ") \n" + "Employed: "
					+ e.getEmploymentDate();
			if (!e.getVehicles().isEmpty()) {
				empinfo += " [ ";
				for (Vehicle v : e.getVehicles()) {
					empinfo += v.getNumberplate() + " (" + (v.isElectric() ? "EV" : "IC") + ") ";
				}
				empinfo += "]";
				enable(2);
			} else {
				empinfo += " [ NO VEHICLES ]";
			}

		}
		txtEmployeeInfo.setText(empinfo);
	}

	private void findAvailableClicked() {
		LocalDate from = getDate(dpFrom);
		LocalDate to = getDate(dpTo);
		if (from == null || to == null) {
			JOptionPane.showMessageDialog(this, "Choose the dates.");
			return;
		}
		try {
			List<ParkingSpot> spots = rc.findAvailableParkingSpots(from, to);
			displayPossibleSpots(spots);
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Can't find spots to park on.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void displayPossibleSpots(List<ParkingSpot> spots) {
		stm = new SpotTableModel();
		stm.setData(spots);
		tblSpots.setModel(stm);
	}

	private LocalDate getDate(UCNDatePicker field) {
		LocalDate date = field.getValue();
		return date;
	}

	private void pickParkingSpotClicked() {
		ParkingSpot pickedSpot = stm.getDataAt(tblSpots.getSelectedRow());
		int id = pickedSpot.getId();
		rc.pickParkingSpot(id, LocalDate.now()); 
	}

	private void saveClicked() {
		pickParkingSpotClicked();
		try {
			rc.confirm();
			cancelClicked();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Can't find spots to park on.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void enable(int step) {
		dpFrom.setEnabled(step > 1);
		dpTo.setEnabled(step > 1);
		btnSearchSpot.setEnabled(step > 1);
		tblSpots.setEnabled(step > 1);
		btnOK.setEnabled(step > 2);
		btnCancel.setEnabled(true);
	}

	private void cancelClicked() {
		this.setVisible(false);
		this.dispose();
	}
}
