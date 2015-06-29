package de.wk.date.holiday;

import de.wk.User;
import de.wk.algorithms.Algorithm;

public class HolidayCalculator {

	private User user;
	private Algorithm algorithm;

	public HolidayCalculator(User user) {
		this.user = user;
		this.algorithm = null;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void calculate() {
		if (!this.algorithm.equals(null)) {
			algorithm.calculate(this.user);
		}
	}
}
