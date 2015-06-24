package de.wk.algorithms;

import de.wk.User;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.day.Holidays;

public interface Algorithm {
	public Holidays calculate(CalendarYear year, User user);

}
