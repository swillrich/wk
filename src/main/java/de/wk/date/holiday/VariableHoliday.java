package de.wk.date.holiday;

import org.joda.time.DateTime;

@SuppressWarnings("rawtypes")
public class VariableHoliday extends Holiday {

	public VariableHoliday(String name, int year, int month, int day) {
		super(name, year, month, day);
	}
	
	public VariableHoliday(String name, DateTime dateTime) {
		super(name, dateTime);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return null;
	}

}
