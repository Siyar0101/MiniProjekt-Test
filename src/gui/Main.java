package gui;

import java.awt.EventQueue;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// RentParkingSpotWindow frame = new RentParkingSpotWindow();
					// frame.setVisible(true);
					RentAgreementsWindow.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
