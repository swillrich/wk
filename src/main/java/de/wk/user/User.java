package de.wk.user;

import de.wk.date.Days;
import de.wk.date.holiday.HolidayProvider.State;

public class User {
	private String name;
	private UserConfiguration userConfiguration;
	private Days holidays;
	private State state;

	public User(String name, int numberOfHolidays, State state) {
		this.name = name;
		this.userConfiguration = new UserConfiguration(numberOfHolidays);
		this.holidays = new Days();
		this.state = state;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Days getHolidays() {
		return this.holidays;
	}

	public String getName() {
		return this.name;
	}

	public UserConfiguration getUserConfiguration() {
		return userConfiguration;
	}

	public State getState() {
		return this.state;
	}
}
