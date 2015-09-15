package de.wk.algorithms;

import de.wk.domain.user.User;

/**
 * A holiday calculator algorithm is used for calculating holidays for a
 * specific range/interval with specific parameter given by user.
 */
public interface HolidayCalculatorAlgorithm {
	public void calculate(User user);
}
