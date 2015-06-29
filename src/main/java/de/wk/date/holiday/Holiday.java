package de.wk.date.holiday;

import org.joda.time.DateTime;

import de.wk.date.WKDateTime;

@SuppressWarnings("rawtypes")
public abstract class Holiday<T extends Holiday> extends WKDateTime {
	private String name;

	public Holiday(String name, int year, int month, int day) {
		this.dateTime = new DateTime(year, month, day, 0, 0, 0);
		this.name = name;
	}

	public Holiday(String name, T holiday) {
		this.dateTime = dependsOn(holiday);
		this.name = name;
	}

	public Holiday(T holiday) {
		this.dateTime = dependsOn(holiday);
		this.name = this.getClass().getSimpleName();
	}

	public Holiday(String name, DateTime dateTime) {
		this.dateTime = dateTime;
		this.name = name;
	}

	public DateTime getDate() {
		return dateTime;
	}

	public String getName() {
		return name;
	}

	public abstract DateTime dependsOn(T day);
}
