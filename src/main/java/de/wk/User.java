package de.wk;

import de.wk.algorithms.DateConstraint;
import de.wk.date.Days;

public class User {
	private String name;
	private final int numberOfHolidays;
	private Integer remainingNumberOfHolidays;
	private Days holidays;
	private DateConstraint dateConstraint = new DateConstraint();

	public User(String name, int numberOfHolidays) {
		this.name = name;
		this.numberOfHolidays = numberOfHolidays;
		this.remainingNumberOfHolidays = numberOfHolidays;
		this.holidays = new Days();
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfHolidays() {
		return this.numberOfHolidays;
	}


	public Days getHolidays() {
		return this.holidays;
	}

	public String getName() {
		return this.name;
	}

	public int getRemainingNumberOfHolidays() {
		return this.remainingNumberOfHolidays;
	}

	public void setRemainingNumberOfHolidays(int remainingNumberOfHolidays) {
		this.remainingNumberOfHolidays = remainingNumberOfHolidays;
	}

	public DateConstraint getDateConstraint() {
		return dateConstraint;
	}
}
