package de.wk.user;

import de.wk.date.Days;
import de.wk.date.holiday.HolidayProvider.State;

/**
 * The user class represents one arbitrary user whose holiday partition should
 * be calculated. The user consist of a name, a state, the user configuration
 * (see class UserConfiguration), the holidays (which are calculated) and a
 * state, in which the user is located.
 */
public class User {
	private String name;
	private UserConfiguration userConfiguration;
	private Days holidays;
	private State state;

	/**
	 * Creates a user instance
	 * 
	 * @param name
	 *            The name of the user
	 * @param numberOfHolidays
	 *            The number of available holidays of the user
	 * @param state
	 *            The state, in which the user is located
	 */
	public User(String name, int numberOfHolidays, State state) {
		this.name = name;
		this.userConfiguration = new UserConfiguration(numberOfHolidays);
		this.holidays = new Days();
		this.state = state;
	}

	/**
	 * Sets the name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the several holidays (default is a empty collection, which is
	 * only filled after holiday calculating)
	 */
	public Days getHolidays() {
		return this.holidays;
	}

	/**
	 * Returns the name of the user
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the user configuration
	 */
	public UserConfiguration getUserConfiguration() {
		return userConfiguration;
	}

	/**
	 * Returns the state, in which the user is located
	 */
	public State getState() {
		return this.state;
	}
}
