package controller;

import db.DataAccessException;
import db.EmployeeDAO;
import db.EmployeeDB;
import model.Employee;

public class EmployeeController {
	private EmployeeDAO empDao;

	public EmployeeController() throws DataAccessException {
		empDao = new EmployeeDB();
	}

	public Employee findByInitials(String initials) throws DataAccessException {
		Employee e = empDao.findByInitials(initials, true);
		return e;
	}

}
