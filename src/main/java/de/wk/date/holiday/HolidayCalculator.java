package de.wk.date.holiday;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.Log;
import de.wk.algorithms.HolidayCalculatorAlgorithm;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.user.HolidayInterval;
import de.wk.user.User;

/**
 * This class calculates for a specific user and with a specific algorithm
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
	 * determined days are getting stored in the user object.
	 */
	public void calculate() {
		Log.out("Calculation for " + user.getName());
		fillPriorityIntervalsWithHolidays();
		Log.out("After filling preferred holiday intervals, "
				+ user.getRemainingHolidays().get()
				+ " holiday(s) are remaining");
		if (!this.algorithm.equals(null)) {
			algorithm.calculate(this.user);
		}
		Log.out("After algorithm run, " + user.getRemainingHolidays().get()
				+ " holiday(s) are remaining");
	}

	private void fillPriorityIntervalsWithHolidays() {
		Iterator<HolidayInterval> iterator = this.user.getHolidayIntervalSet()
				.toIterator();
		while (iterator.hasNext()) {
			Interval interval = iterator.next().getInterval();
			for (DateTime dateTime = interval.getStart(); (interval
					.contains(dateTime) || dateTime
					.compareTo(interval.getEnd()) == 0)
					&& this.user.getRemainingHolidays().stillAvailable(); dateTime = dateTime
					.plusDays(1)) {
				KindOfDay kindOf = this.user.getHolidays().determineKindOf(
						dateTime);
				if (kindOf == KindOfDay.WEEK) {
					VariableHoliday variableHoliday = new VariableHoliday(
							"being preferred holiday", dateTime);
					this.user.getHolidays().add(variableHoliday);
					this.user.getRemainingHolidays().decrement();
				}
			}
		}
	}
}
