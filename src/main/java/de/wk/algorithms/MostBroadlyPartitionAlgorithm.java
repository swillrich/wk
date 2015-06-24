package de.wk.algorithms;

import de.wk.User;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.day.Holidays;

public class MostBroadlyPartitionAlgorithm implements Algorithm {
	
	@Override
	public Holidays calculate(CalendarYear year, User user) {
		
		return user.getHolidays();
	}

}
