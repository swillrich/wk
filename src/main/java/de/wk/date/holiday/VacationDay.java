package de.wk.date.holiday;

import org.joda.time.DateTime;

import de.wk.date.WKDateTime;

/**
 * The VariableHoliday is a holiday which is not official, but corresponds to a
 * user preference. In that way the option is provided to set user-preferred
 * holidays, which should be considered while holiday calculating. The user can
 * express days which should be a holiday definitively.
 */
public class VacationDay extends WKDateTime {

	/**
	 * @param name
	 *            The name of the holiday, which is optional
	 * @param year
	 *            The year, in which the holiday occurs
	 * @param month
	 *            The month, in which the holiday occurs
	 * @param day
	 *            The day, on which the holiday occurs
	 */
	public VacationDay(String name, int year, int month, int day) {
		super(name, year, month, day);
	}

	/**
	 * @param name
	 *            The name of the holiday, which is optional
	 * @param dateTime
	 *            The DateTime on which the holiday occurs
	 */
	public VacationDay(String name, DateTime dateTime) {
		super(name, dateTime);
	}

}
