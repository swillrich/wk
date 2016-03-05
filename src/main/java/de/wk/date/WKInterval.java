package de.wk.date;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.MutableInterval;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;

import de.wk.algorithms.Gap;
import de.wk.date.WKDateTime.KindOfDay;

/**
 * This WKInterval class representing an interval which consist of a start and
 * end date.
 */
public class WKInterval implements ReadableInterval {
	private String title;

	/**
	 * The interval representing the start and end date.
	 */
	private Interval interval;
	private Days days = new Days();

	/**
	 * The title of the interval. This information is optionally.
	 * 
	 * @return The title as a string if it is given. Otherwise null.
	 */
	public String getTitle() {
		return title;
	}

	public void initializeDays() {
		for (DateTime dt = interval.getStart(); dt.compareTo(interval.getEnd()) <= 0; dt = dt.plusDays(1)) {
			WKDateTime wkDateTime = new WKDateTime(dt);
			days.add(wkDateTime);
		}
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
		initializeDays();
		try {
			days.replaceDay(end);
			days.replaceDay(start);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		initializeDays();
	}

	/**
	 * @return The Joda Interval
	 */
	public Interval getInterval() {
		return interval;
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

	@Override
	public String toString() {
		try {
			WKDateTime first = days.get(0);
			KindOfDay kindOf = days.determineKindOf(first.getDateTime());
			return String.format("%-20s%-20s%-4s%-10s%-15s%n", (this instanceof Gap ? "GAP" : "PART").concat(" starts with"),
					first.toString(), "as", kindOf.name(), "with " + days.size() + " days");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Chronology getChronology() {
		return this.interval.getChronology();
	}

	@Override
	public long getStartMillis() {
		return this.interval.getStartMillis();
	}

	@Override
	public DateTime getStart() {
		return this.interval.getStart();
	}

	@Override
	public long getEndMillis() {
		return this.interval.getEndMillis();
	}

	@Override
	public DateTime getEnd() {
		return this.interval.getEnd();
	}

	@Override
	public boolean contains(ReadableInstant instant) {
		return this.interval.contains(instant);
	}

	@Override
	public boolean contains(ReadableInterval interval) {
		return this.interval.contains(interval);
	}

	@Override
	public boolean overlaps(ReadableInterval interval) {
		return this.interval.overlaps(interval);
	}

	@Override
	public boolean isAfter(ReadableInstant instant) {
		return this.interval.isAfter(instant);
	}

	@Override
	public boolean isAfter(ReadableInterval interval) {
		return this.interval.isAfter(interval);
	}

	@Override
	public boolean isBefore(ReadableInstant instant) {
		return this.interval.isBefore(instant);
	}

	@Override
	public boolean isBefore(ReadableInterval interval) {
		return this.interval.isBefore(interval);
	}

	@Override
	public Interval toInterval() {
		return this.interval.toInterval();
	}

	@Override
	public MutableInterval toMutableInterval() {
		return this.interval.toMutableInterval();
	}

	@Override
	public Duration toDuration() {
		return this.interval.toDuration();
	}

	@Override
	public long toDurationMillis() {
		return this.interval.toDurationMillis();
	}

	@Override
	public Period toPeriod() {
		return this.interval.toPeriod();
	}

	@Override
	public Period toPeriod(PeriodType type) {
		return this.interval.toPeriod(type);
	}
}
