package de.wk.holiday.day;

import org.joda.time.DateTime;

@SuppressWarnings("rawtypes")
public class BussUndBettag extends Holiday {

	@SuppressWarnings("unchecked")
	public BussUndBettag(String string, Holiday day) {
		super(string, day);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		DateTime cmd = day.getDate();
		return cmd.minusDays(cmd.getDayOfWeek()).minusDays(32);
	}

}
