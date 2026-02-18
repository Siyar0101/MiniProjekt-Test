package db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.ParkingSpot;
import model.RentalAgreement;

public class RentalAgreementDB implements RentalAgreementDAO {
	private static final String INSERT = """
			 INSERT INTO rentagreement
			    (startdate, enddate, monthlyfee, employee_id, parkingspot_id)
			VALUES (?, ?, ?, ?, ?)
			""";
	private static final String SELECT_ALL = "select id, startdate, enddate, monthlyfee, employee_id, parkingspot_id from rentagreement ra";
	
	private PreparedStatement insert, selectAll;
	private EmployeeDAO empDB;
	private ParkingSpotDAO psDB;
	
	public RentalAgreementDB() throws DataAccessException {
		try {
			empDB = new EmployeeDB();
			psDB = new ParkingSpotDB();
			insert = DBConnection.getInstance().getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			selectAll = DBConnection.getInstance().getConnection().prepareStatement(SELECT_ALL);
		} catch (SQLException e) {
			throw new DataAccessException("Could not prepare statements for rentagreement.", e);
		}
	}

	@Override
	public boolean save(RentalAgreement a) throws DataAccessException {
		try {
			DBConnection.getInstance().startTransaction();
			
			if(!checkAvailable(a) ) {
				throw new DataAccessException("Parking spot " + a.getParkingSpot().getNumber() + " no longer availabe " +
						"\nbetween " + a.getStartDate() + " and " + a.getEndDate(), null);
			}
			
			insert.setDate(1, Date.valueOf(a.getStartDate()));
			insert.setDate(2, Date.valueOf(a.getStartDate())); 
			insert.setDouble(3, a.getMonthlyFee());
			insert.setInt(4, a.getEmployee().getId());
			insert.setInt(5, a.getParkingSpot().getId());
			int raId = DBConnection.getInstance().executeInsertWithIdentity(insert);
			a.setId(raId);
			DBConnection.getInstance().commitTransaction();
			return true;
		} catch (SQLException e) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new DataAccessException("Failed rollback insert rentagreement", e1);
			}
			throw new DataAccessException("Could not prepare statement or execute insert for inserting rentagreement", e);
		}
	}
	
	private boolean checkAvailable(RentalAgreement a) throws DataAccessException {
		List<ParkingSpot> pss = psDB.findAvailable(a.getStartDate(), a.getEndDate());
		boolean found = false;
		for(int i = 0; i < pss.size() && !found; i++) {
			found = pss.get(i).getId() == a.getParkingSpot().getId();
		}
		return found; 
	}

	@Override
	public List<RentalAgreement> findAll(boolean fullAssociation) throws DataAccessException {
		try {
			ResultSet rs = selectAll.executeQuery();
			List<RentalAgreement> res = buildObjects(rs, fullAssociation);
			return res;
		} catch (SQLException e) {
			throw new DataAccessException("Could not retrieve all Rental Agreements", e);
		}
		
	}

	private List<RentalAgreement> buildObjects(ResultSet rs, boolean fullAssociation) throws DataAccessException {
		List<RentalAgreement> res = new ArrayList<>();
		RentalAgreement ra = buildObject(rs, fullAssociation);
		while(ra != null) {
			res.add(ra);
			ra = buildObject(rs, fullAssociation);
		}
		return res;
	}

	private RentalAgreement buildObject(ResultSet rs, boolean fullAssociation) throws DataAccessException {
		RentalAgreement res = null;
		try {
		if(rs.next()) {
			res = new RentalAgreement(
					rs.getInt("id"),
					rs.getDate("startdate").toLocalDate(),
					rs.getDate("enddate").toLocalDate(),
					rs.getDouble("monthlyfee"),
					new Employee(rs.getInt("employee_id")),
					new ParkingSpot(rs.getInt("parkingspot_id"))
			);
			if(fullAssociation) {
				//we retrieve full associations, otherwise do not match with equals when compare
				Employee e = empDB.findById(res.getEmployee().getId(), true);
				res.setEmployee(e);
				ParkingSpot ps = psDB.findById(res.getParkingSpot().getId());
				res.setParkingSpot(ps);
			}
		}
		} catch(SQLException e) {
			throw new DataAccessException("Could not retrieve Rental Agreement from database", e);
		}
		return res;
	}

}
