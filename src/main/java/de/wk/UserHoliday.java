package de.wk;

import org.joda.time.DateTime;

import de.wk.holiday.Month;
import de.wk.holiday.day.Holiday;

@SuppressWarnings("rawtypes")
public class UserHoliday extends Holiday {

	public UserHoliday(String name, int year, Month month, int day) {
		super(name, year, month, day);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return null;
	}

}
