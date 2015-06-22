package de.wk.holiday.day;

import org.joda.time.DateTime;

import de.wk.holiday.Month;

@SuppressWarnings("rawtypes")
public class FixHoliday extends Holiday {

	public FixHoliday(String name, int year, Month month, int day) {
		super(name, year, month, day);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return null;
	}

}
