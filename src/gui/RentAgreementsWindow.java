package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.RentController;
import db.DataAccessException;
import model.RentalAgreement;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class RentAgreementsWindow extends JFrame {
	private static RentAgreementsWindow instance = null;
	private RentController rentCtrl;

	private JPanel contentPane;
	private JTable tblRentAgreements;
	private RentAgreementTableModel ratm;

	private RentAgreementMonitor monitorThread;

	/**
	 * Launch the application.
	 */
	public static void open() {
		instance = new RentAgreementsWindow();
		instance.setVisible(true);
	}

	public static synchronized RentAgreementsWindow getInstance() {
		if (instance == null) {
			open();
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	private RentAgreementsWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 474);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnuFile = new JMenu("File");
		menuBar.add(mnuFile);

		JMenuItem mniExit = new JMenuItem("Exit");
		mniExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitClicked();
			}
		});
		mnuFile.add(mniExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnAddRentalAgreement = new JButton("Add...");
		btnAddRentalAgreement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRentalAgreementClicked();
			}
		});
		panel.add(btnAddRentalAgreement);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		tblRentAgreements = new JTable();
		scrollPane.setViewportView(tblRentAgreements);

		init();
	}

	private void init() {
		try {
			rentCtrl = new RentController();
			ratm = new RentAgreementTableModel();
			tblRentAgreements.setModel(ratm);
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this,
					"An error occurred while trying to connect to the data store\nThe application exits.");
			e.printStackTrace();
			System.exit(ERROR);
		}

		monitorThread = new RentAgreementMonitor(); 
		new Thread(monitorThread).start();
	}

	private void exitClicked() {
		if (JOptionPane.showConfirmDialog(this, "Exit?", "Exiting",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			this.dispose();
			System.exit(NORMAL);
		}
	}

	private void addRentalAgreementClicked() {
		RentParkingSpotWindow rpsw = new RentParkingSpotWindow();
		rpsw.setVisible(true);
	}

	// update the gui on the edt
	public void updateRentalAgreementList(List<RentalAgreement> data) {
		SwingUtilities.invokeLater(() -> {
			int selectedRow = tblRentAgreements.getSelectedRow();
			this.ratm.setData(data);
			if (selectedRow > -1) {
				tblRentAgreements.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
	}

	// Stop the monitoring thread when closing the application
	@Override
	public void dispose() {
		super.dispose();
		monitorThread.stopMonitoring(); // Stop the monitoring loop
	}

}
