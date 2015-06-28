package de.wk;

import org.joda.time.DateTime;

import de.wk.algorithms.MostBroadlyPartitionAlgorithm;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.HolidayPrinter;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) {
		CalendarYear year = new CalendarYear(2015);
		User sascha = new User("Sascha :-)", 24, year);
		sascha.getDateConstraint().setStartingDay(new DateTime().now());
		
		HolidayCalculator calc = new HolidayCalculator(sascha, year);
		calc.setAlgorithm(new MostBroadlyPartitionAlgorithm());
		calc.calculate();
		
		new HolidayPrinter(sascha.getHolidays(), true, true).print(
				new DateTime(2015, 1, 1, 0, 0),
				new DateTime(2016, 1, 1, 0, 0).minusDays(1));

	}
}
