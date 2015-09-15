package de.wk;

import org.joda.time.Interval;

import de.wk.date.Days;
import de.wk.date.DaysPrinter;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayCalculator;
import de.wk.date.holiday.HolidayProvider;
import de.wk.date.holiday.HolidayProvider.State;
import de.wk.user.User;

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

		Days holidays = HolidayProvider.provideBy(2015, null);
		sascha.getHolidays().addAll(holidays);

		HolidayCalculator calculator = new HolidayCalculator(sascha);
		calculator.calculate();

		new DaysPrinter(sascha.getHolidays(), true, true).print(interval);
	}
}
