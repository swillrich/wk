package de.wk.user;

import de.wk.date.Days;

public class User {
	private String name;
	private UserConfiguration userConfiguration;
	private Days holidays;

	public User(String name, int numberOfHolidays) {
		this.name = name;
		this.userConfiguration = new UserConfiguration(numberOfHolidays);
		this.holidays = new Days();
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
}
