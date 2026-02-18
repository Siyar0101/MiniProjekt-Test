package db;

import java.util.List;

import model.RentalAgreement;

public interface RentalAgreementDAO {

	boolean save(RentalAgreement a) throws DataAccessException;

	List<RentalAgreement> findAll(boolean fullAssociation) throws DataAccessException;

}
