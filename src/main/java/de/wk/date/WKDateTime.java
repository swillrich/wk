package de.wk.date;

import java.util.Locale;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class is the upper class for all date-specific objects related to this
 * component. Furthermore this class implements the ReadableDateFormat interface
 * of the Joda library and is therefore compatible with a lot of functionality
 * provided by Joda.
 */
public class WKDateTime implements ReadableDateTime {

	protected DateTime dateTime = null;

	/**
	 * A day has a certain characteristic, referred to as "kind of". For
	 * instance, this kind of characteristic designates this day being a weekend
	 * day or a holiday.
	 */
	public static enum KindOfDay {
		WEEKEND, WEEK, HOLIDAY, VACATIONDAY;
		private DateTime dateTime;

		/**
		 * A KindOfDay instance can hold a specific date
		 * 
		 * @param dateTime
		 *            The date on on which this instance is based upon.
		 * @return the instance on which the operation is invoked
		 */
		public KindOfDay setDateTime(DateTime dateTime) {
			this.dateTime = dateTime;
			return this;
		}

		public DateTime getDateTime() {
			return dateTime;
		}
	}

	public WKDateTime(DateTime jodaDateTime) {
		dateTime = jodaDateTime;
	}

	/**
	 * Creates an instance of WEKDateTime by year, month and day
	 * 
	 * @param year
	 *            The year, in which the day occurs
	 * @param month
	 *            The month, in which the day occurs
	 * @param day
	 *            The day, on which the new date should based on
	 */
	public WKDateTime(int year, int month, int day) {
		dateTime = new DateTime(year, month, day, 0, 0, 0);
	}

	/**
	 * Creates a new date instance based on the current date/time
	 */
	public WKDateTime() {
		this.dateTime = DateTime.now();
	}

	@Override
	public int compareTo(ReadableInstant arg0) {
		return dateTime.compareTo(arg0);
	}

	@Override
	public long getMillis() {
		return dateTime.getMillis();
	}

	@Override
	public Chronology getChronology() {
		return dateTime.getChronology();
	}

	@Override
	public DateTimeZone getZone() {
		return dateTime.getZone();
	}

	@Override
	public int get(DateTimeFieldType type) {
		return dateTime.get(type);
	}

	@Override
	public boolean isSupported(DateTimeFieldType field) {
		return dateTime.isSupported(field);
	}

	@Override
	public Instant toInstant() {
		return dateTime.toInstant();
	}

	@Override
	public boolean isEqual(ReadableInstant instant) {
		return dateTime.isEqual(instant);
	}

	@Override
	public boolean isAfter(ReadableInstant instant) {
		return dateTime.isAfter(instant);
	}

	@Override
	public boolean isBefore(ReadableInstant instant) {
		return dateTime.isBefore(instant);
	}

	@Override
	public int getDayOfWeek() {
		return dateTime.getDayOfWeek();
	}

	@Override
	public int getDayOfMonth() {
		return dateTime.getDayOfMonth();
	}

	@Override
	public int getDayOfYear() {
		return dateTime.getDayOfYear();
	}

	@Override
	public int getWeekOfWeekyear() {
		return dateTime.getWeekOfWeekyear();
	}

	@Override
	public int getWeekyear() {
		return dateTime.getWeekyear();
	}

	@Override
	public int getMonthOfYear() {
		return dateTime.getMonthOfYear();
	}

	@Override
	public int getYear() {
		return dateTime.getYear();
	}

	@Override
	public int getYearOfEra() {
		return dateTime.getYearOfEra();
	}

	@Override
	public int getYearOfCentury() {
		return dateTime.getYearOfCentury();
	}

	@Override
	public int getCenturyOfEra() {
		return dateTime.getCenturyOfEra();
	}

	@Override
	public int getEra() {
		return dateTime.getEra();
	}

	@Override
	public int getMillisOfSecond() {
		return dateTime.getMillisOfSecond();
	}

	@Override
	public int getMillisOfDay() {
		return dateTime.getMillisOfDay();
	}

	@Override
	public int getSecondOfMinute() {
		return dateTime.getSecondOfMinute();
	}

	@Override
	public int getSecondOfDay() {
		return dateTime.getSecondOfDay();
	}

	@Override
	public int getMinuteOfHour() {
		return dateTime.getMinuteOfHour();
	}

	@Override
	public int getMinuteOfDay() {
		return dateTime.getMinuteOfDay();
	}

	@Override
	public int getHourOfDay() {
		return dateTime.getHourOfDay();
	}

	@Override
	public DateTime toDateTime() {
		return dateTime.toDateTime();
	}

	@Override
	public MutableDateTime toMutableDateTime() {
		return dateTime.toMutableDateTime();
	}

	@Override
	public String toString(String pattern) throws IllegalArgumentException {
		return dateTime.toString(pattern);
	}

	@Override
	public String toString(String pattern, Locale locale)
			throws IllegalArgumentException {
		return dateTime.toString(pattern, locale);
	}

	@Override
	public String toString() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd. MMMM yyyy");
		return fmt.print(this);
	}

	/**
	 * This class is used for providing a customized date time type and works
	 * internally with the Joda datetime type. This Joda datetime will returned.
	 */
	public DateTime getJodaDateTime() {
		return dateTime;
	}
}
