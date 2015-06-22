package de.wk.holiday.day;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.wk.holiday.Month;

public abstract class Holiday<T extends Holiday> implements
		Comparable<Holiday<?>> {
	private DateTime date;
	private String name;

	public Holiday(String name, int year, Month month, int day) {
		this.date = new DateTime(year, Month.numberOfMonth(month), day, 0, 0, 0);
		this.name = name;
	}

	public Holiday(String name, T holiday) {
		this.date = dependsOn(holiday);
		this.name = name;
	}

	public Holiday(T holiday) {
		this.date = dependsOn(holiday);
		this.name = this.getClass().getSimpleName();
	}

	public DateTime getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public abstract DateTime dependsOn(T day);

	@Override
	public String toString() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd. MMMM yyyy");
		return name + ": " + fmt.print(date);
	}

	@Override
	public int compareTo(Holiday<?> o) {
		return this.getDate().compareTo(o.getDate());
	}
}
