package de.wk;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import de.wk.algorithms.DPSolve01KPAlgorithm;
import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayCalculator;
import de.wk.date.holiday.HolidayProvider;
import de.wk.date.holiday.HolidayProvider.State;
import de.wk.user.User;

public class MainTest {

	private User user;

	@Before
	public void initSampleData() throws Exception {
		WKDateTime startTime = new WKDateTime(2016, 1, 1);
		Interval interval = new Interval(startTime, startTime.getJodaDateTime().plusYears(1).minusDays(1));

		user = new User("Sascha", 24, State.BE, interval);

		Days holidays = HolidayProvider.provideBy(interval, null);
		user.getHolidays().mergeDays(holidays);
	}

	@Test
	public void testWithSpecificData() throws Exception {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new DPSolve01KPAlgorithm());
		calculator.calculate();
//		new DaysPrinter(user.getHolidays()).print(user.getScope());
	}

}
