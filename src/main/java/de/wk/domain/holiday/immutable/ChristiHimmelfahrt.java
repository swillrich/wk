package de.wk.domain.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.domain.holiday.Holiday;

/**
 * The holiday representation of the official holiday "Christi Himmelfahrt"
 */
public class ChristiHimmelfahrt extends Holiday<Ostersonntag> {

	public ChristiHimmelfahrt(String name, Ostersonntag holiday) {
		super(name, holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(39);
	}

}
