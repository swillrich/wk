package de.wk;

import org.joda.time.DateTime;

import de.wk.algorithms.SimpleAlgorithm;
import de.wk.holiday.CalendarYear;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		CalendarYear year2015 = new CalendarYear(2015);
		User alfred = new User("alfred", 6);
		HolidayCalculator calc = new HolidayCalculator(alfred, year2015);
		calc.setAlgorithm(new SimpleAlgorithm());
		calc.calculate();
		alfred.getHolidays().addAll(year2015.getAllHolidays());
		alfred.getHolidays().printWithin(new DateTime(2015, 1, 1, 0, 0),
				new DateTime(2016, 1, 1, 0, 0).minusDays(1), true);

	}
}
