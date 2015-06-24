package de.wk.holiday;

import org.joda.time.DateTime;

import de.wk.holiday.day.Holiday;

@SuppressWarnings("rawtypes")
public class UserHoliday extends Holiday {

	public UserHoliday(String name, int year, Month month, int day) {
		super(name, year, month, day);
	}
	
	public UserHoliday(String name, DateTime dateTime) {
		super(name, dateTime);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return null;
	}

}
