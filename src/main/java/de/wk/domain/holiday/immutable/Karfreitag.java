package de.wk.domain.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.domain.holiday.Holiday;

/**
 * The holiday representation of the official holiday "Karfreitag"
 */
public class Karfreitag extends Holiday<Ostersonntag> {

	public Karfreitag(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().minusDays(2);
	}

}
