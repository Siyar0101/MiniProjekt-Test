package controller;

import java.time.LocalDate;
import java.util.List;

import db.DataAccessException;
import db.ParkingSpotDAO;
import db.ParkingSpotDB;
import model.ParkingSpot;

public class ParkingSpotController {
	
	private ParkingSpotDAO psDao;
	
	
	public ParkingSpotController () throws DataAccessException {
		psDao = new ParkingSpotDB();
	}
	
	public List<ParkingSpot> findAvailableParkingSpots(LocalDate fromDate, LocalDate toDate) throws DataAccessException {
		List<ParkingSpot> pp = psDao.findAvailable(fromDate, toDate);		
		return pp;
	}

}