package model;

import java.util.Objects;

public class ParkingSpot {
	private int id;
	private int number;
	private double baseMonthlyFee;

	public ParkingSpot(int id, int number, double baseMonthlyFee) {
		this(id);
		this.number = number;
		this.baseMonthlyFee = baseMonthlyFee;
	}
	public ParkingSpot(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getBaseMonthlyFee() {
		return baseMonthlyFee;
	}
	public void setBaseMonthlyFee(double baseMonthlyFee) {
		this.baseMonthlyFee = baseMonthlyFee;
	}
	@Override
	public String toString() {
		return "ParkingSpot [id=" + id + ", number=" + number + ", baseMonthlyFee=" + baseMonthlyFee + "]";
	}

     @Override
     public boolean equals(Object o) {
         if (this == o) 
             return true;
         if (!(o instanceof ParkingSpot)) 
             return false;
         ParkingSpot parkingspot = (ParkingSpot) o;
         return this.id == parkingspot.id && this.number == parkingspot.number  && this.baseMonthlyFee == parkingspot.baseMonthlyFee;
     }
     
     @Override
     public final int hashCode() {
         return Objects.hash(id, number, baseMonthlyFee);
     }
	
}
