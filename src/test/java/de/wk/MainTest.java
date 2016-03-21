package de.wk;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import de.wk.algorithms.DPSolve01KPAlgorithm;
import de.wk.algorithms.FillWithRemainingHolidaysAlgorithm;
import de.wk.date.Days;
import de.wk.date.DaysPrinter;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayCalculator;
import de.wk.date.holiday.HolidayProvider;
import de.wk.date.holiday.HolidayProvider.State;
import de.wk.user.User;

public class MainTest {

	private User user;

	@Before
	public void initSampleData() throws Exception {
		WKDateTime startTime = new WKDateTime(2016, 3, 21);
		Interval interval = new Interval(startTime, new WKDateTime(2017, 1, 1));

		user = new User("Sven", 19, State.BE, interval);

		Days holidays = HolidayProvider.provideBy(interval, null);
		user.getDays().mergeDays(holidays);
	}

	@Test
	public void testWithSpecificData() throws Exception {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.addAlgorithm(new DPSolve01KPAlgorithm());
		calculator.addAlgorithm(new FillWithRemainingHolidaysAlgorithm());
		calculator.calculate();
		new DaysPrinter(user.getDays()).print(user.getScope());
		System.out.println("Number of remaining holidays: " + user.getRemainingHolidays().get());
	}

}
