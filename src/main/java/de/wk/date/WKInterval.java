package de.wk.date;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Interval;

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

	public Days getDaysBetween() {
		Days days = new Days();
		for (DateTime dt = interval.getStart(); dt.compareTo(interval.getEnd()) <= 0; dt = dt.plusDays(1)) {
			WKDateTime wkDateTime = new WKDateTime(dt);
			days.add(wkDateTime);
		}
		return days;
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

	public WKInterval() {

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
		Days days = new Days();
		for (DateTime date = this.interval.getStart(); date.compareTo(this.interval.getEnd()) < 1; date = date
				.plusDays(1)) {
			days.add(new WKDateTime(date));
		}
		return days.iterator();
	}

	/**
	 * Get the size of an interval. Here a null interval is meant as the
	 * previous WKInterval of a gap, if this gap is the first within the given
	 * scope (same for the next WKInterval of the last gap within the given
	 * scope). For these two cases we set the size of the interval to 0 although
	 * an interval cannot have a length 0 practically.
	 * 
	 * We have to increment the number of days within an interval, because of
	 * the way intervals are created internally. The start of the interval is
	 * the begin of the first day. Also, the end of the interval is the begin of
	 * the last day. For more information see
	 * {@link org.joda.time.Interval#Interval(org.joda.time.ReadableInstant, org.joda.time.ReadableInstant)}
	 * 
	 * @return The length of the interval.
	 */
	public int getSize() {
		if (interval == null) {
			return 0;
		} else {
			return (int) interval.toDuration().getStandardDays() + 1;
		}
	}
}
