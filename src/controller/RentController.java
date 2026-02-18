package controller;

import java.time.LocalDate;
import java.util.List;

import db.DataAccessException;
import db.RentalAgreementDAO;
import db.RentalAgreementDB;
import model.Employee;
import model.ParkingSpot;
import model.RentalAgreement;
import model.Vehicle;

public class RentController {
	private FeeCalculator fc;
	private RentalAgreement ra;
	private List<ParkingSpot> parkingSpotCache;
	private EmployeeController ec;
	private ParkingSpotController plc;
	private RentalAgreementDAO raDao;

	public RentController() throws DataAccessException {
		this(null, null);
	}

	public RentController(EmployeeController eCtrl, ParkingSpotController psCtrl) throws DataAccessException {
		ec = eCtrl;
		if (ec == null) {
			this.ec = new EmployeeController();
		}

		this.plc = psCtrl;
		if (plc == null) {
			this.plc = new ParkingSpotController();
		}

		this.fc = new FeeCalculator();
		this.raDao = new RentalAgreementDB();
	}

	public RentalAgreement getRa() {
		return ra;
	}

	/**
	 * Returns the RentalAgreement that is created with the Employee that matches
	 * the initials supplied. Should the Employee found not own any vehicles, null
	 * is returned.
	 * 
	 * @param initials
	 * @return
	 * @throws DataAccessException
	 */
	public RentalAgreement enterEmployeeInitials2(String initials) throws DataAccessException {
		Employee e = ec.findByInitials(initials);
		if (e != null && !e.getVehicles().isEmpty()) {
			ra = new RentalAgreement(e);
		} else {
			ra = null;
		}
		return ra;
	}

	public Employee enterEmployeeInitials(String initials) throws DataAccessException {
		Employee e = ec.findByInitials(initials);
		if (e != null ) {
			ra = new RentalAgreement(e);
		} else {
			ra = null;
		}
		return e;
	}

	public List<ParkingSpot> findAvailableParkingSpots(LocalDate fromDate, LocalDate toDate)
			throws DataAccessException {
		List<ParkingSpot> pp = plc.findAvailableParkingSpots(fromDate, toDate);
		parkingSpotCache = pp;
		ra.setStartDate(fromDate);
		ra.setEndDate(toDate);
		return pp;
	}


	public RentalAgreement pickParkingSpot(int spotId, LocalDate currentDate) {
		ParkingSpot spot = findCachedSpot(spotId);
		double baseMonthlyFee = spot.getBaseMonthlyFee();

		Employee e = ra.getEmployee();
		LocalDate employmentDate = e.getEmploymentDate();
		int seniority = this.fc.calcSeniority(employmentDate, currentDate);

		List<Vehicle> vv = e.getVehicles();
		boolean hasElectric = this.fc.calcGreen(vv);
		double fee = fc.calcFee(baseMonthlyFee, seniority, hasElectric);

		ra.setMonthlyFee(fee);
		ra.setParkingSpot(spot);

		return ra;
	}

	private ParkingSpot findCachedSpot(int spotId) {
		ParkingSpot res = null;
		for (int i = 0; i < parkingSpotCache.size() && res == null; i++) {
			if (parkingSpotCache.get(i).getId() == spotId) {
				res = parkingSpotCache.get(i);
			}
		}
		return res;
	}

	public boolean confirm() throws DataAccessException {
		boolean res = raDao.save(ra);
		return res;
	}

	public List<RentalAgreement> findAll() throws DataAccessException {
		List<RentalAgreement> res = raDao.findAll(true);
		return res;
	}

}
