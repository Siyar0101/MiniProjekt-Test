package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Employee;
import model.ParkingSpot;
import model.RentalAgreement;
import model.Vehicle;

/**
 * Utility class used by all the @Before methods in the test suite that access
 * the database. All tables are dropped, re-created and filled with data. By
 * dropping the table, we reset the auto-generated indices s.t. IDENTITY(1,1)
 * starts on 1 and we can also count on the ids of the records to be the same in
 * all cases.
 * 
 * @author knol
 * @version 2017-02-20
 * @author gibe
 * @version feb. 2018 - mar. 2023 - juli 2023
 *
 */

public class TestUtilitiesDB {

	public static void main(String[] args) throws SQLException, IOException, DataAccessException {
		cleanDB(); // call to the utility class that resets the database
		System.out.println("cleaned");
		createTablesDB();
		System.out.println("restored tables");	
		insertDataDB();
		System.out.println("restored data");
	}

	public static void cleanDB() throws SQLException, IOException, DataAccessException {

		try (Statement stmt = DBConnection.getInstance().getConnection().createStatement()) {
			String sqlClean = readAllBytesJava("scripts/mptestDropTables.sql");
			stmt.executeUpdate(sqlClean);
		}
	}

	public static void createTablesDB() throws SQLException, IOException, DataAccessException {
		try (Statement stmt = DBConnection.getInstance().getConnection().createStatement()) {

			String sqlRestore = readAllBytesJava("scripts/mptestCreateTables.sql");
			stmt.executeUpdate(sqlRestore);

		}
	}

	public static void insertDataDB() throws SQLException, IOException, DataAccessException {
		try (Statement stmt = DBConnection.getInstance().getConnection().createStatement()) {

			String sqlInsert = readAllBytesJava("scripts/mptestInsertData.sql");
			stmt.executeUpdate(sqlInsert);
		}

	}

	// Read file content into string with - Files.readAllBytes(Path path)

	private static String readAllBytesJava(String filePath) throws IOException {
		String content = "";
		content = new String(Files.readAllBytes(Paths.get(filePath)));
		return content;
	}



}
