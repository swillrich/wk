package de.wk.date.holiday;

import org.joda.time.DateTime;

import de.wk.date.WKDateTime;

/**
 * This class is used to represent a (official) holiday and is a WKDateTime.
 * This class can be used to instantiate a arbitrary holiday.
 * 
 * @param <T>
 *            The generic type for each sub holiday type.
 */
@SuppressWarnings("rawtypes")
public abstract class Holiday<T extends Holiday> extends WKDateTime {
	private String name;

	/**
	 * Instantiates a arbitrary holiday by means of knowing the year, month and
	 * the precise day.
	 * 
	 * @param name
	 *            the name of the holiday
	 * @param year
	 *            the year, in which the holiday occurs
	 * @param month
	 *            the month, in which the holiday occurs
	 * @param day
	 *            the specific day, on that the holiday occurs
	 */
	public Holiday(String name, int year, int month, int day) {
		this.dateTime = new DateTime(year, month, day, 0, 0, 0);
		this.name = name;
	}

	/**
	 * Instantiates a specific holiday through a WKDateTime object.
	 * 
	 * @param name
	 *            the name of the holiday
	 * @param holiday
	 *            the already initialized/instantiated WKDateTime
	 */
	public Holiday(String name, T holiday) {
		this.dateTime = dependsOn(holiday);
		this.name = name;
	}

	/**
	 * Instantiates a specific holiday through a WKDateTime object without a
	 * given name.
	 * 
	 * The name of this object will be "Holiday".
	 * 
	 * @param holiday
	 *            the already initialized, instantiated WKDateTime
	 */
	public Holiday(T holiday) {
		this.dateTime = dependsOn(holiday);
		this.name = this.getClass().getSimpleName();
	}

	/**
	 * Instantiates a specific holiday through a DateTime object.
	 * 
	 * @param name
	 *            The name of the holiday
	 * @param dateTime
	 *            The specific date the holiday occurs
	 */
	public Holiday(String name, DateTime dateTime) {
		this.dateTime = dateTime;
		this.name = name;
	}

	/**
	 * Returns the date of this holiday object
	 */
	public DateTime getDate() {
		return dateTime;
	}

	/**
	 * Returns the name of this holiday object
	 */
	public String getName() {
		return name;
	}

	public abstract DateTime dependsOn(T day);
}
