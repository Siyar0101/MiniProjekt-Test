package db;

import java.util.List;

import model.Vehicle;

public interface VehicleDAO {

	List<Vehicle> findByEmployeeId(int id) throws DataAccessException;

}
