package de.wk.date.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.date.holiday.Holiday;

/**
 * The holiday representation of the official holiday "Fronleichnam"
 *
 */
public class Fronleichnam extends Holiday<Ostersonntag> {

	/**
	 * @param holiday
	 *            The object, on which this holiday depends
	 */
	public Fronleichnam(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(60);
	}

}
