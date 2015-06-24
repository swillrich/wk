package de.wk;

import de.wk.holiday.day.Holidays;

public class User {
	private String name;
	private int numberOfHolidays;
	private Holidays holidays;

	public User(String name, int numberOfHolidays) {
		this.name = name;
		this.numberOfHolidays = numberOfHolidays;
		this.holidays = new Holidays();
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfHolidays() {
		return this.numberOfHolidays;
	}

	public void setNumberOfHolidays(int numberOfHolidays) {
		this.numberOfHolidays = numberOfHolidays;
	}

	public Holidays getHolidays() {
		return holidays;
	}

	public void setHolidays(Holidays holidays) {
		this.holidays = holidays;
	}
}
