package gui;

import java.util.List;

import controller.RentController;
import db.DataAccessException;
import model.RentalAgreement;

public class RentAgreementMonitor implements Runnable {

	private boolean running = true; // Flag to control the monitoring loop


	public void stopMonitoring() {
		running = false; // Stop the monitoring loop
	}

	@Override
	public void run() {

		while (running) {
			// Mimic some work with the print
			List<RentalAgreement> data = null;
			try {
				data = new RentController().findAll();
			} catch (DataAccessException e) {
				e.printStackTrace();
			}

			// Notify the observer (JFrame)
			if (data != null) {
				RentAgreementsWindow.getInstance().updateRentalAgreementList(data);
			}

			// Poll every 3 seconds
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
