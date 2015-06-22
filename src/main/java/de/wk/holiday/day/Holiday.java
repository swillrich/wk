package de.wk.holiday.day;

import org.joda.time.DateTime;

import de.wk.holiday.Month;

public abstract class Holiday<T extends Holiday> {
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

	public DateTime getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public abstract DateTime dependsOn(T day);
}
