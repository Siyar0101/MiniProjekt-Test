package model;

import java.time.LocalDate;
import java.util.Objects;

public class Vehicle {
	private int id;
	private String numberplate;
	private boolean electric;
	private LocalDate registrationDate;
	public Vehicle(int id, String numberplate, boolean electric, LocalDate registrationDate) {
		super();
		this.id = id;
		this.numberplate = numberplate;
		this.electric = electric;
		this.registrationDate = registrationDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumberplate() {
		return numberplate;
	}
	public void setNumberplate(String numberplate) {
		this.numberplate = numberplate;
	}
	public boolean isElectric() {
		return electric;
	}
	public void setElectric(boolean electric) {
		this.electric = electric;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", numberplate=" + numberplate + ", electric=" + electric + ", registrationDate="
				+ registrationDate + "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof Vehicle)) 
            return false;
        Vehicle vehicle = (Vehicle) o;
        return this.id == vehicle.id 
        		&& Objects.equals(this.numberplate,vehicle.numberplate)  
        		&& this.electric == vehicle.electric
        		&& Objects.equals(this.registrationDate, vehicle.registrationDate); 
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(id, numberplate, electric, registrationDate);
    }

}
