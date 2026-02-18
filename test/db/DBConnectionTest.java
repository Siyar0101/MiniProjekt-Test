package db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

class DBConnectionTest {


	@Test
	public void testGetConnection() {
			Connection c;
			try {
				c = DBConnection.getInstance().getConnection();
				assertNotNull(c);
			} catch (DataAccessException e) {
				fail();
			}
			
	}
}
