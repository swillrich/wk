package de.wk.date.holiday;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.Log;
import de.wk.algorithms.HolidayCalculatorAlgorithm;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.user.User;
import de.wk.user.VacationInterval;

/**
 * This class calculates for a specific user and with a specific algorithm
 * holidays. The result of the calculation is strongly depending on the chosen
 * algorithm.
 */
public class HolidayCalculator {

	private User user;
	private List<HolidayCalculatorAlgorithm> algorithms;

	/**
	 * The user whose holidays should be calculated
	 */
	public HolidayCalculator(User user) {
		this.user = user;
		this.algorithms = new ArrayList<HolidayCalculatorAlgorithm>();
	}

	/**
	 * @param algorithm
	 *            The algorithm which should apply
	 */
	public void addAlgorithm(HolidayCalculatorAlgorithm algorithm) {
		this.algorithms.add(algorithm);
	}

	/**
	 * By calling this method the algorithm computes the holidays. The
	 * determined days are getting stored in the user object.
	 * 
	 * @throws Exception
	 */
	public void calculate() throws Exception {
		Log.out("Calculation for " + user.getName());
		for (HolidayCalculatorAlgorithm alg : this.algorithms) {
			alg.calculate(this.user);
		}
		Log.out("After algorithms run, " + user.getRemainingHolidays().get() + " holiday(s) are remaining");
	}
}
