package de.wk;

import de.wk.algorithms.Algorithm;
import de.wk.holiday.CalendarYear;

public class HolidayCalculator {

	private User user;
	private CalendarYear year;
	private Algorithm algorithm;

	public HolidayCalculator(User user, CalendarYear year) {
		this.user = user;
		this.year = year;
		this.algorithm = null;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void calculate() {
		if (!this.algorithm.equals(null)) {
			this.user.setHolidays(algorithm.calculate(this.year, this.user));
		}
	}
}
