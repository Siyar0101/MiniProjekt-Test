package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
	private int id;
	private String initials;
	private String firstName;
	private String lastName;
	private LocalDate employmentDate;
	private List<Vehicle> vehicles;
	
	public Employee(int id, String initials, String firstName, String lastName, LocalDate employmentDate) {
		this(id);
		this.initials = initials;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employmentDate = employmentDate;
		this.vehicles = new ArrayList<>();
	}
	
	public Employee(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getEmploymentDate() {
		return employmentDate;
	}
	public void setEmploymentDate(LocalDate employmentDate) {
		this.employmentDate = employmentDate;
	}
	public void addVehicle(Vehicle v) {
		vehicles.add(v);
	}
	public List<Vehicle> getVehicles() {
		return new ArrayList<>(vehicles);
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", employmentDate="
				+ employmentDate + ", vehicles=" + vehicles + "]";
	}

	public void setVehicles(List<Vehicle> vv) {
		vehicles.clear();
		if(vv != null) {
			vehicles.addAll(vv);
		}
	}
	
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof Employee)) 
            return false;
        Employee employee = (Employee) o;
        return this.id == employee.id 
        		&& Objects.equals(this.initials, employee.initials)
        		&& Objects.equals(this.firstName, employee.firstName)
        		&& Objects.equals(this.lastName, employee.lastName)
        		&& Objects.equals(this.employmentDate, employee.employmentDate)
        		&& Objects.equals(this.vehicles, employee.vehicles);
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(id, initials, firstName, lastName, employmentDate, vehicles);
    }
}
