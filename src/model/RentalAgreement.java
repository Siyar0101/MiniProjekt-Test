package model;

import java.time.LocalDate;
import java.util.Objects;

public class RentalAgreement {
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private double monthlyFee;
	private Employee employee;
	private ParkingSpot parkingSpot;
	public RentalAgreement(int id, LocalDate startDate, LocalDate endDate, double monthlyFee, Employee employee,
			ParkingSpot parkingSpot) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyFee = monthlyFee;
		this.employee = employee;
		this.parkingSpot = parkingSpot;
	}
	public RentalAgreement(Employee e) {
		this(-1, null, null, -1d, e, null);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public double getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}
	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}
	@Override
	public String toString() {
		return "RentalAgreement [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", monthlyFee="
				+ monthlyFee + ", employee=" + employee + ", parkingSpot=" + parkingSpot + "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof RentalAgreement)) 
            return false;
        RentalAgreement rentalAgreement = (RentalAgreement) o;
        return this.id == rentalAgreement.id 
        		&& Objects.equals(this.startDate, rentalAgreement.startDate)
        		&& Objects.equals(this.endDate, rentalAgreement.endDate)
        		&& this.monthlyFee == rentalAgreement.monthlyFee
        		&& Objects.equals(this.employee, rentalAgreement.employee)
        		&& Objects.equals(this.parkingSpot, rentalAgreement.parkingSpot);
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(id, startDate, endDate, monthlyFee, employee, parkingSpot);
    }
	
}
