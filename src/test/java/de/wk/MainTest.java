package de.wk;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import de.wk.algorithms.BiggestPartitionAlgorithm;
import de.wk.algorithms.FillGapsAlgorithm;
import de.wk.domain.WKDateTime;
import de.wk.domain.holiday.Holidays;
import de.wk.domain.user.HolidayInterval;
import de.wk.domain.user.User;
import de.wk.holidaycalculation.DaysPrinter;
import de.wk.holidaycalculation.HolidayCalculator;
import de.wk.holidaycalculation.HolidayProvider;
import de.wk.holidaycalculation.HolidayProvider.State;

public class MainTest {

	private User user;
	private Interval interval;

	@Before
	public void initSampleData() throws Exception {
		user = new User("Sascha", 24, State.BE, 2015);

		WKDateTime time = new WKDateTime(2015, 1, 1);
		interval = new Interval(time, time.getJodaDateTime().plusYears(1).minusDays(1));

		Holidays holidays = HolidayProvider.provideBy(interval, null);
		user.getHolidays().addAll(holidays);
	}

	@Test
	public void testWithSpecificData() {
		User user = new User("Sven", 15, State.BE, new WKDateTime(2015, 9, 10), new WKDateTime(2015, 12, 31));
		HolidayInterval chrismasInterval = new HolidayInterval(new WKDateTime(2015, 12, 23),
				new WKDateTime(2015, 12, 31), true);
		chrismasInterval.setTitle("Christmas holdays");
		user.getHolidayIntervalSet().add(chrismasInterval);

		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.calculate();
		new DaysPrinter(user.getHolidays()).print(user.getScope());
	}

	@Test
	public void testSimpleAlgorithm() {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAnotherAlgorithm(new FillGapsAlgorithm());
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

	@Test
	public void testBiggestPartitionAlgorithm() {
		WKDateTime currentDate = new WKDateTime();
		DateTime endDate = new WKDateTime(2016, 1, 1).getJodaDateTime().minusDays(1);

		User user = new User("Sascha", 24, State.BE, currentDate, new WKDateTime(endDate));

		Holidays days = HolidayProvider.provideBy(user.getScope(), null);
		user.getHolidays().clear();
		user.getHolidays().addAll(days);

		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}

	@Test
	public void testBothAlgorithms() {
		HolidayCalculator calculator = new HolidayCalculator(user);
		calculator.setAnotherAlgorithm(new FillGapsAlgorithm());
		calculator.calculate();
		new DaysPrinter(user.getHolidays(), true, true).print(interval);
		calculator.calculate();

		new DaysPrinter(user.getHolidays(), true, true).print(interval);
	}
}
