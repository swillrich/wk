package de.wk.user;

import org.joda.time.Interval;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.HolidayProvider;
import de.wk.date.holiday.HolidayProvider.State;

/**
 * The user class represents one arbitrary user whose holiday partition should
 * be calculated. The user consist of a name, a state, the user configuration
 * (see class UserConfiguration), the holidays (which are calculated) and a
 * state, in which the user is located.
 */
public class User {
	/**
	 * Name of the user
	 */
	private String name;
	/**
	 * All the holidays of the user
	 */
	private Days holidays;
	/**
	 * The total number of initial available holidays. Not to be confused with
	 * remainingNumberOfHolidays, which is mutable.
	 */
	private final int numberOfHolidays;
	/**
	 * The remaining number of holidays as custom type. The type holds a integer
	 * with the current number of holidays the user owns. Appropriate methods
	 * allow to value changes.
	 */
	private RemainingNumberOfHolidays remainingNumberOfHolidays;
	/**
	 * The state of the user.
	 */
	private State state;
	/**
	 * The considered scope in which the calculated holidays could take place.
	 */
	private Interval scope;
	/**
	 * The list of intervals the user <b>want to be filled</b> with holidays.
	 */
	private IntervalSet preferreadHolidayInteralSet;
	/**
	 * The list of intervals the user <b>don't want to be filled</b> with
	 * holidays.
	 */
	private IntervalSet nonHoliadayIntervals = new IntervalSet();

	public int getNumberOfHolidays() {
		return numberOfHolidays;
	}

	public IntervalSet getNonHoliadayIntervals() {
		return nonHoliadayIntervals;
	}

	public IntervalSet getPreferreadHolidayIntervalSet() {
		return preferreadHolidayInteralSet;
	}

	/**
	 * Creates a user instance
	 * 
	 * @param name
	 *            The name of the user
	 * @param numberOfHolidays
	 *            The number of available holidays of the user
	 * @param state
	 *            The state, in which the user is located
	 * @param start
	 *            The first date of the scope to be considered
	 * @param end
	 *            The last date of the scope to be considered
	 */
	public User(String name, int numberOfHolidays, State state, WKDateTime start, WKDateTime end) {
		this.numberOfHolidays = numberOfHolidays;
		init(name, state, start, end);
	}

	/**
	 * Creates a user instance
	 * 
	 * @param name
	 *            The name of the user
	 * @param numberOfHolidays
	 *            The number of available holidays of the user
	 * @param state
	 *            The state, in which the user is located
	 * @param year
	 *            The year to be considered
	 */
	public User(String name, int numberOfHolidays, State state, int year) {
		this.numberOfHolidays = numberOfHolidays;
		init(name, state, new WKDateTime(year, 1, 1), new WKDateTime(year, 12, 31));
	}

	private void init(String name, State state, WKDateTime start, WKDateTime end) {
		this.name = name;
		this.scope = new Interval(start, end);
		this.holidays = new Days();
		this.state = state;
		this.remainingNumberOfHolidays = new RemainingNumberOfHolidays(numberOfHolidays);
		preferreadHolidayInteralSet = new IntervalSet(numberOfHolidays);
	}

	/**
	 * Calculates the fix holidays for this user. While the calculation is
	 * processed, the given State is considered.
	 * 
	 * @return The user object on which the current method is invoked
	 */
	public User setHolidaysByGivenConfiguration() {
		Days days = HolidayProvider.provideBy(this.scope, this.state);
		this.holidays.addAll(days);
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Interval getScope() {
		return scope;
	}

	public void setScope(Interval scope) {
		this.scope = scope;
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
	 * Returns the state, in which the user is located
	 */
	public State getState() {
		return this.state;
	}

	/**
	 * Returns the current number of remaining holidays. For more information,
	 * consider the class java-doc.
	 * 
	 * @return The class with the current number
	 */
	public RemainingNumberOfHolidays getRemainingHolidays() {
		return remainingNumberOfHolidays;
	}
}
