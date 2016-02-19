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
		setName(name);
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
		setName(this.getClass().getSimpleName());
	}


	public Holiday(String name, int year, int month, int day) {
		super(name, year, month, day);
	}

	/**
	 * Returns the date of this holiday object
	 */
	public DateTime getDate() {
		return dateTime;
	}

	public abstract DateTime dependsOn(T day);
}
