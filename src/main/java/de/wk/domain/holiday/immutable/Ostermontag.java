package de.wk.domain.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.domain.holiday.Holiday;

/**
 * The holiday representation of the official holiday "Ostermontag"
 */
public class Ostermontag extends Holiday<Ostersonntag> {

	/**
	 * @param holiday
	 *            The object, on which this holiday depends
	 */
	public Ostermontag(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(1);
	}

}
