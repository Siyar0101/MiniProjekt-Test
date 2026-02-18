package db;

import java.time.LocalDate;
import java.util.List;

import model.ParkingSpot;

public interface ParkingSpotDAO {


	List<ParkingSpot> findAvailable(LocalDate fromDate, LocalDate toDate)
			throws DataAccessException;

	ParkingSpot findById(int id) throws DataAccessException;

}
