package de.wk.holiday.day;

import org.joda.time.DateTime;

import de.wk.holiday.Month;

@SuppressWarnings("rawtypes")
public class BussUndBettag extends Holiday {

	@SuppressWarnings("unchecked")
	public BussUndBettag(int year) {
		super(new FixHoliday(null, year, Month.December, 25));
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return day.getDate().minusDays(32);
	}

}
