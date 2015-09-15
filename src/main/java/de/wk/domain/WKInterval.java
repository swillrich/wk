package de.wk.domain;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.domain.holiday.Holidays;

/**
 * This WKInterval class representing an interval which consist of a start and
 * end date.
 */
public class WKInterval {
	private String title;

	/**
	 * The interval representing the start and end date.
	 */
	private Interval interval;

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
	public WKInterval(WKDateTime start, WKDateTime end) {
		this.interval = new Interval(start, end);
	}

	/**
	 * @param interval
	 *            The interval with start and end
	 * @param isHoliday
	 *            Means whether the type of the interval is holiday or
	 *            non-holiday.
	 */
	public WKInterval(Interval interval) {
		this.interval = interval;
	}

	/**
	 * @return The Joda Interval
	 */
	public Interval getInterval() {
		return interval;
	}

	/**
	 * Does return the iterator to go through each item (as WKDateTime)
	 * contained by this interval.
	 * 
	 * @return Iterator
	 */
	public Iterator<WKDateTime> getIterator() {
		Holidays days = new Holidays();
		for (DateTime date = this.interval.getStart(); date
				.compareTo(this.interval.getEnd()) < 1; date = date.plusDays(1)) {
			days.add(new WKDateTime(date));
		}
		return days.iterator();
	}
}
