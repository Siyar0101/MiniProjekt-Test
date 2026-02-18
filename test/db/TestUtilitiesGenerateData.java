package db;

import java.time.LocalDate;

import model.Employee;
import model.ParkingSpot;
import model.RentalAgreement;
import model.Vehicle;

public class TestUtilitiesGenerateData {
	
	private TestUtilitiesGenerateData() {
		
	}
	
	/* GENERATE DATA EXAMPLE */ 
	public static Employee generateEmployee() {
		Employee e = new Employee(1, "aand", "Anders", "And", LocalDate.of(2022, 12, 1));
		e.addVehicle(new Vehicle(1, "AA13133", false, LocalDate.of(2022, 12, 1)));
		e.addVehicle(new Vehicle(2, "AA13134", true, LocalDate.of(2023, 2, 1)));
		return e;
	}
	
	public static ParkingSpot generateParkingSpotChosen() {
		return new ParkingSpot(1,1,100.0);
	}

	public static RentalAgreement generateRentalAgreement() {
		Employee e = generateEmployee();
		ParkingSpot ps = generateParkingSpotChosen();
		LocalDate from = LocalDate.of(2025,1,1);
		LocalDate to = LocalDate.of(2025,8,1);    
		return new RentalAgreement(4, from, to, 100.0d, e, ps);
	}
	


}
