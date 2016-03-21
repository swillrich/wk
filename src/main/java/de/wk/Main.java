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
	public static void main(String[] args) throws Exception {
		WKDateTime time = new WKDateTime(2015, 1, 1);
		Interval interval = new Interval(time, time.getJodaDateTime()
				.plusYears(1).minusDays(1));
		
		User sascha = new User("Sven :-)", 24, State.BE, interval);

		Days holidays = HolidayProvider.provideBy(2015, null);
		sascha.getDays().addAll(holidays);

		HolidayCalculator calculator = new HolidayCalculator(sascha);
		calculator.addAlgorithm(null);
		calculator.calculate();

		new DaysPrinter(sascha.getDays(), true, true).print(interval);
	}
}
