package de.wk;

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
		System.out.println(alfred.getHolidayList());
	}
}
