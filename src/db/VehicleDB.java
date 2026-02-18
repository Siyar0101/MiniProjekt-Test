package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Vehicle;

public class VehicleDB implements VehicleDAO {
	private static final String SELECT_ALL = "select id, numberplate, electric, registrationdate, employee_id from vehicle";
	private static final String SELECT_BY_EMPLOYEE_ID = SELECT_ALL + " where employee_id = ?";
	private PreparedStatement selectByEmpid;
	
	public VehicleDB() throws DataAccessException {
		try {
			selectByEmpid = DBConnection.getInstance().getConnection().prepareStatement(SELECT_BY_EMPLOYEE_ID);
		} catch (SQLException e) {
			throw new DataAccessException("Could not prepare statements for vehicle", e);
		}
	}
	
	@Override
	public List<Vehicle> findByEmployeeId(int eid) throws DataAccessException {
		try {
			selectByEmpid.setInt(1, eid);
			ResultSet rs = selectByEmpid.executeQuery();
			List<Vehicle> res = buildObjects(rs);
			return res;
		} catch (SQLException e) {
			throw new DataAccessException("could not bind params or select in Vehicle", e);
		}
	}

	private List<Vehicle> buildObjects(ResultSet rs) throws DataAccessException {
		List<Vehicle> res = new ArrayList<>();
		Vehicle v = buildObject(rs);
		while(v != null) {
			res.add(v);
			v = buildObject(rs);
		}
		return res;
	}

	private Vehicle buildObject(ResultSet rs) throws DataAccessException {
		Vehicle res = null;
		try {
			if(rs.next()) {
				res = new Vehicle(
						rs.getInt("id"),
						rs.getString("numberplate"),
						rs.getBoolean("electric"),
						rs.getDate("registrationdate").toLocalDate());
			}
		} catch (SQLException e) {
			throw new DataAccessException("could not read result set for vehicle", e);
		}
		return res;
	}

}
