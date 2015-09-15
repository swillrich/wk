package de.wk.domain.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.domain.holiday.Holiday;

/**
 * The holiday representation of the official holiday "Buss und Bettag"
 */
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
