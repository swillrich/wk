package de.wk.date.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.date.holiday.Holiday;

@SuppressWarnings("rawtypes")
public class FixHoliday extends Holiday {

	public FixHoliday(String name, int year, int month, int day) {
		super(name, year, month, day);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return null;
	}

}
