package de.wk.date.holiday;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.algorithms.HolidayCalculatorAlgorithm;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.user.User;
import de.wk.user.WKInterval;

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
	 * By calling this method the algorithm computes the holidays. The
	 * determined days are getting stored in the user object.<br>
	 * Note that the first step will be to fill the Intervals of priority (see
	 * class DateConstraints).
	 */
	public void calculate() {
		fillPriorityIntervalsWithHolidays();
		if (!this.algorithm.equals(null)) {
			algorithm.calculate(this.user);
		}
		System.out.println("Algorithm processed for user: " + user.getName());
		System.out.println("Remaining number of holidays after algorithm run: " + user.getRemainingHolidays().get());
	}

	private void fillPriorityIntervalsWithHolidays() {
		for (WKInterval wkInterval : this.user.getPreferredHolidayIntervals()) {
			Interval interval = wkInterval.getInterval();
			for (DateTime dateTime = interval.getStart(); interval.contains(dateTime)
					|| dateTime.compareTo(interval.getEnd()) == 0; dateTime = dateTime.plusDays(1)) {
				KindOfDay kindOf = this.user.getHolidays().determineKindOf(dateTime);
				if (kindOf == KindOfDay.WEEK) {
					VariableHoliday variableHoliday = new VariableHoliday("must be holiday", dateTime);
					this.user.getHolidays().add(variableHoliday);
				}
			}
		}
	}
}
