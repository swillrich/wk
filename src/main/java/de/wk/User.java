package de.wk;

import de.wk.algorithms.DateConstraint;
import de.wk.date.Days;

public class User {
	private String name;
	private int numberOfHolidays;
	private Integer remainNumberOfHolidays;
	private Days holidays;
	private DateConstraint dateConstraint = new DateConstraint();

	public User(String name, int numberOfHolidays) {
		this.name = name;
		this.numberOfHolidays = numberOfHolidays;
		this.remainNumberOfHolidays = numberOfHolidays;
		this.holidays = new Days();
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

	public Days getHolidays() {
		return holidays;
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
