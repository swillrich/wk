package de.wk;

import de.wk.algorithms.DateConstraint;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.day.Holidays;

public class User {
	private String name;
	private int numberOfHolidays;
	private Integer remainNumberOfHolidays;
	private Holidays holidays;
	private DateConstraint dateConstraint = new DateConstraint();

	public User(String name, int numberOfHolidays) {
		this.name = name;
		this.numberOfHolidays = numberOfHolidays;
		this.remainNumberOfHolidays = numberOfHolidays;
		this.holidays = new Holidays();
	}

	public User(String name, int numberOfHolidays, CalendarYear initHollidays) {
		this.name = name;
		this.numberOfHolidays = numberOfHolidays;
		this.remainNumberOfHolidays = numberOfHolidays;
		this.holidays = new Holidays();
		this.holidays.addAll(initHollidays.getAllHolidays());
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

	public String getName() {
		return name;
	}

	public int getRemainNumberOfHolidays() {
		return remainNumberOfHolidays;
	}

	public void setRemainNumberOfHolidays(int remainNumberOfHolidays) {
		this.remainNumberOfHolidays = remainNumberOfHolidays;
	}

	public DateConstraint getDateConstraint() {
		return dateConstraint;
	}
}
