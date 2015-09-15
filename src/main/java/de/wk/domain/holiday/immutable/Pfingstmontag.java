package de.wk.domain.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.domain.holiday.Holiday;

/**
 * The holiday representation of the official holiday "Pfingstmontag"
 *
 */
public class Pfingstmontag extends Holiday<Ostersonntag> {

	/**
	 * @param holiday
	 *            The object, on which this holiday depends
	 */
	public Pfingstmontag(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(50);
	}

}
