package de.wk;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import de.wk.algorithms.BiggestPartitionAlgorithm;
import de.wk.algorithms.SimpleAlgorithm;
import de.wk.date.Days;
import de.wk.date.DaysPrinter;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayCalculator;
import de.wk.date.holiday.HolidayProvider;
import de.wk.date.holiday.HolidayProvider.State;
import de.wk.user.User;

public class MainTest {

	private User user;
	private Interval interval;

	@Before
	public void initSampleData() throws Exception {
		user = new User("Sacke :-)", 24, State.BE);

		WKDateTime time = new WKDateTime(2015, 1, 1);
		interval = new Interval(time, time.getJodaDateTime().plusYears(1)
				.minusDays(1));

		Days holidays = new HolidayProvider().provideBy(interval);
		user.getHolidays().addAll(holidays);
	}

	@Test
	public void testSimpleAlgorithm() {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new SimpleAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

	@Test
	public void testBiggestPartitionAlgorithm() {
		user.getUserConfiguration().setMaxLengthOfHolidayPartition(7);
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new BiggestPartitionAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

	@Test
	public void testBothAlgorithms() {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new SimpleAlgorithm());
		calculator.calculate();
		new DaysPrinter(user.getHolidays(), true, true).print(interval);
		calculator.setAlgorithm(new BiggestPartitionAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

}
