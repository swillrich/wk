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

public class WKDateTime implements ReadableDateTime {

	protected DateTime dateTime = null;

	public WKDateTime(int year, int month, int day) {
		dateTime = new DateTime(year, month, day, 0, 0, 0);
	}

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

	public DateTime getJodaDateTime() {
		return dateTime;
	}
}
