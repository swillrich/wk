package de.wk;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import de.wk.algorithms.BiggestPartitionAlgorithm;
import de.wk.algorithms.FillGapsAlgorithm;
import de.wk.date.Days;
import de.wk.date.DaysPrinter;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayCalculator;
import de.wk.date.holiday.HolidayProvider;
import de.wk.date.holiday.HolidayProvider.State;
import de.wk.date.holiday.immutable.ChristiHimmelfahrt;
import de.wk.user.User;
import de.wk.user.WKInterval;

public class MainTest {

	private User user;
	private Interval interval;

	@Before
	public void initSampleData() throws Exception {
		user = new User("Sacke :-)", 24, State.BE, 2015);

		WKDateTime time = new WKDateTime(2015, 1, 1);
		interval = new Interval(time, time.getJodaDateTime().plusYears(1).minusDays(1));

		Days holidays = HolidayProvider.provideBy(interval, null);
		user.getHolidays().addAll(holidays);
	}

	@Test
	public void testWithSpecificData() {
		User user = new User("Sven", 24, State.BE, 2015).setHolidaysByGivenConfiguration();
		WKInterval chrismasInterval = new WKInterval(new WKDateTime(2015, 12, 23), new WKDateTime(2015, 12, 31), true);
		chrismasInterval.setTitle("Christmas holdays");
		user.getPreferreadHolidayInteralSet().add(chrismasInterval);

		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new BiggestPartitionAlgorithm());
		calculator.calculate();
		new DaysPrinter(user.getHolidays()).print(user.getScope());
	}

	@Test
	public void testSimpleAlgorithm() {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new FillGapsAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

	@Test
	public void testBiggestPartitionAlgorithm() {
		WKDateTime currentDate = new WKDateTime();
		DateTime endDate = new WKDateTime(2016, 1, 1).getJodaDateTime().minusDays(1);
		user.setScope(new Interval(currentDate, endDate));

		Days days = HolidayProvider.provideBy(user.getScope(), null);
		user.getHolidays().clear();
		user.getHolidays().addAll(days);

		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new BiggestPartitionAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

	@Test
	public void testBothAlgorithms() {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAlgorithm(new FillGapsAlgorithm());
		calculator.calculate();
		new DaysPrinter(user.getHolidays(), true, true).print(interval);
		calculator.setAlgorithm(new BiggestPartitionAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

}
