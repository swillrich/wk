package de.wk.date.holiday;

import de.wk.algorithms.HolidayCalculatorAlgorithm;
import de.wk.user.User;

/**
 * This class calculated for a specific user and with a specific algorithm the
 * holidays. The result of the calculation is strongly depending on the chosen
 * algorithm.
 */
public class HolidayCalculator {

	private User user;
	private HolidayCalculatorAlgorithm algorithm;

	/**
	 * The user whose holidays should be calculated
	 */
	public HolidayCalculator(User user) {
		this.user = user;
		this.algorithm = null;
	}

	/**
	 * @param algorithm
	 *            The algorithm which should apply
	 */
	public void setAlgorithm(HolidayCalculatorAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * By calling this method the algorithm compute the holidays. The determined
	 * days are getting stored in the user object.
	 */
	public void calculate() {
		if (!this.algorithm.equals(null)) {
			algorithm.calculate(this.user);
		}
	}
}
