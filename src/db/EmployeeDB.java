package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Employee;
import model.Vehicle;

public class EmployeeDB implements EmployeeDAO {
	private VehicleDAO vehicleDao;
	
	private static final String SELECT_ALL_Q = 
			"select id, initials, fname, lname, employmentdate from employee";
	private static final String SELECT_BY_INITS = 
			SELECT_ALL_Q + " where initials = ?";
	private static final String SELECT_BY_ID = SELECT_ALL_Q + " where id = ?";
	
	private PreparedStatement selectByInits;
	private PreparedStatement selectById;
	
	public EmployeeDB() throws DataAccessException {
		vehicleDao = new VehicleDB();
		try {
			selectByInits = DBConnection.getInstance().getConnection().prepareStatement(SELECT_BY_INITS);
			selectById = DBConnection.getInstance().getConnection().prepareStatement(SELECT_BY_ID);
		} catch (SQLException e) {
			throw new DataAccessException("Could not prepare statements",e);
		}
		
	}

	@Override
	public Employee findByInitials(String initials, boolean fullAssociation) throws DataAccessException {
		try {
			selectByInits.setString(1, initials);
			ResultSet rs = selectByInits.executeQuery();
			Employee e = buildObject(rs, fullAssociation);
			return e;
		} catch (SQLException e) {
			throw new DataAccessException("Could not bind param or select employee by initials", e);
		}
	}
	

	@Override
	public Employee findById(int id, boolean fullAssociation) throws DataAccessException {
		try {
			selectById.setInt(1, id);
			ResultSet rs = selectById.executeQuery();
			Employee e = buildObject(rs, fullAssociation);
			return e;
		} catch (SQLException e) {
			throw new DataAccessException("Could not bind param or select employee by ID", e);
		}
	}

	private Employee buildObject(ResultSet rs, boolean fullAssociation) throws DataAccessException {
		Employee e = null;
		try {
			if(rs.next()) {
				e = new Employee(
						rs.getInt("id"),
						rs.getString("initials"),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getDate("employmentdate").toLocalDate()
						);
				if(fullAssociation) {
					List<Vehicle> vehicles = vehicleDao.findByEmployeeId(e.getId());
					e.setVehicles(vehicles);
				}
			}
		} catch (SQLException e1) {
			throw new DataAccessException("Could not read result set for employee", e1);
		}
		return e;
	}


}
