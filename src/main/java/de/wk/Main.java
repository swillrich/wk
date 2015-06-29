package de.wk;

import org.joda.time.Interval;

import de.wk.algorithms.SimpleAlgorithm;
import de.wk.date.Days;
import de.wk.date.DaysPrinter;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayCalculator;
import de.wk.date.holiday.HolidayProvider;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) {
		User sascha = new User("Sacke :-)", 24);

		WKDateTime time = new WKDateTime(2015, 1, 1);
		Interval interval = new Interval(time, time.getJodaDateTime()
				.plusYears(1).minusDays(1));

		Days holidays = new HolidayProvider().provideBy(2015);
		sascha.getHolidays().addAll(holidays);

		HolidayCalculator calculator = new HolidayCalculator(sascha);
		calculator.setAlgorithm(new SimpleAlgorithm());
		calculator.calculate();

		new DaysPrinter(sascha.getHolidays(), true, true)
				.print(interval);
	}
}
