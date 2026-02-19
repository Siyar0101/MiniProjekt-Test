package controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import model.Vehicle;

public class FeeCalculator {

	protected static final int MIN_YEAR = 1900;
	protected static final String EXCEP_MESSAGE_DATE_NULL = "Date should be not null!";
	protected static final String EXCEP_MESSAGE_DATE_TOO_OLD = "Year should be later than 1900!";
	protected static final String EXCEP_MESSAGE_DATE_EMPL_BEFORE = "Employment date should be before current date";

	/**
	 * Calculates the monthly rental fee based on the business rules.
	 * 
	 * @param baseMonthlyFee the base monthly rental fee before adjustments; must be
	 *                       greater than or equal to 0
	 * 
	 * @param seniority      the number of years the customer has been registered;
	 *                       must be 0 or greater
	 * 
	 * @param hasElectric    indicate eligibility for the extra “green” discount
	 * 
	 * @return the calculated monthly rental fee
	 * @throws IllegalArgumentException if baseMonthlyFee is negative or if
	 *                                  seniority is negative
	 */

	public double calcFee(double baseMonthlyFee, int seniority, boolean hasElectric) {

		if (baseMonthlyFee < 0 || seniority < 0)
			throw new IllegalArgumentException();

		int discountPercentage = (hasElectric ? 20 : 0);

		if (seniority >= 3) {
			discountPercentage += 5;
		}

		baseMonthlyFee *= (1 - discountPercentage / 100d);
		return baseMonthlyFee;
	}

	
	// This should be called with todayDate i.e. LocalDate.now
	public int calcSeniority(LocalDate employmentDate, LocalDate todayDate) {
		if (employmentDate == null || todayDate == null) {
			throw new IllegalArgumentException(EXCEP_MESSAGE_DATE_NULL);
		}
		if (employmentDate.getYear() < MIN_YEAR || todayDate.getYear() < MIN_YEAR) {
			throw new IllegalArgumentException(EXCEP_MESSAGE_DATE_TOO_OLD);
		}
		if (employmentDate.compareTo(todayDate) >= 0) {
			throw new IllegalArgumentException(EXCEP_MESSAGE_DATE_EMPL_BEFORE);
		}
		Period period = employmentDate.until(todayDate);
		int seniority = period.getYears();
		return seniority;
	}

	/**
	 * Determines whether a list of vehicles should be considered "green".
	 *
	 * @param vv the list of vehicles to evaluate; must not be null and must not
	 *           contain null elements
	 * @return true if the list is non-empty and all the vehicles are electric ;
	 *         false otherwise
	 * @throws IllegalArgumentException if the vehicle list is null or contains null
	 *                                  entries
	 */

	public boolean calcGreen(List<Vehicle> vv) {

		if (vv == null)
			throw new IllegalArgumentException("Vehicle list must not be null.");

		boolean green = false;
		if (!vv.isEmpty()) {
			green = true;
			for (Vehicle v : vv) {
				if (v != null) {
					green = green && v.isElectric();
				} else {
					throw new IllegalArgumentException();
				}
			}
		}
		return green;
	}

}
