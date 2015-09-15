package de.wk.domain.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.domain.holiday.Holiday;

/**
 * A holiday is a fix holiday, when his occurrence is every year same. All these
 * holidays are encapsulated within a FixHoliday.
 */
@SuppressWarnings("rawtypes")
public class FixHoliday extends Holiday {

	/**
	 * @param name
	 *            The name of the holiday
	 * @param year
	 *            The year in which the official holiday is contained
	 * @param month
	 *            The month in which the official holiday is contained
	 * @param day
	 *            The specific day on that the holiday occurs
	 */
	public FixHoliday(String name, int year, int month, int day) {
		super(name, year, month, day);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return null;
	}

}
