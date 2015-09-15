package de.wk;

import org.joda.time.Interval;

import de.wk.domain.WKDateTime;
import de.wk.domain.holiday.Holidays;
import de.wk.domain.user.User;
import de.wk.holidaycalculation.DaysPrinter;
import de.wk.holidaycalculation.HolidayCalculator;
import de.wk.holidaycalculation.HolidayProvider;
import de.wk.holidaycalculation.HolidayProvider.State;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) {
		User sascha = new User("Sascha :-)", 24, State.BE, 2015);

		WKDateTime time = new WKDateTime(2015, 1, 1);
		Interval interval = new Interval(time, time.getJodaDateTime()
				.plusYears(1).minusDays(1));

		Holidays holidays = HolidayProvider.provideBy(2015, null);
		sascha.getHolidays().addAll(holidays);

		HolidayCalculator calculator = new HolidayCalculator(sascha);
		calculator.calculate();

		new DaysPrinter(sascha.getHolidays(), true, true).print(interval);
	}
}
