package db;

public class DataAccessException extends Exception {

	private static final long serialVersionUID = 3812074315284055411L;

	public DataAccessException(String message,Exception e) {
		super(message, e);
	}
}
