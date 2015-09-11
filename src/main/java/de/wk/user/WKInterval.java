package de.wk.user;

import org.joda.time.Interval;

import de.wk.date.WKDateTime;

/**
 * This WKInterval class representing an interval which consist of a start and
 * end date. The type of the interval is either holiday or non-holiday.
 */
public class WKInterval {
	private String title;

	/**
	 * The title of the interval. This information is optionally.
	 * 
	 * @return The title as a string if it is given. Otherwise null.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the interval.
	 * 
	 * @param title
	 *            The string as the name of the interval.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The interval representing the start and end date.
	 */
	private Interval interval;

	/**
	 * Indicates whether this interval is a holiday or non-holiday interval.
	 */
	private boolean isHoliday = true;

	/**
	 * Indicates whether this interval is a holiday or non-holiday interval.
	 * 
	 * @return Is true if the interval is representing a holiday interval. False
	 *         otherwise.
	 */
	public boolean isHoliday() {
		return isHoliday;
	}

	/**
	 * Creates a new interval.
	 * 
	 * @param start
	 *            The first date of the interval
	 * @param end
	 *            The last day of the interval
	 * @param isHoliday
	 *            Means whether the type of the interval is holiday or
	 *            non-holiday.
	 */
	public WKInterval(WKDateTime start, WKDateTime end, boolean isHoliday) {
		this.interval = new Interval(start, end);
	}

	/**
	 * @param interval
	 *            The interval with start and end
	 * @param isHoliday
	 *            Means whether the type of the interval is holiday or
	 *            non-holiday.
	 */
	public WKInterval(Interval interval, boolean isHoliday) {
		this.interval = interval;
	}

	/**
	 * @return The Joda Interval
	 */
	public Interval getInterval() {
		return interval;
	}
}
