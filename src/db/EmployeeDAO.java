package db;

import model.Employee;

public interface EmployeeDAO {

	Employee findByInitials(String init, boolean fullAssociation)
		throws DataAccessException;

	Employee findById(int id, boolean fullAssociation) throws DataAccessException;

}
