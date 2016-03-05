package de.wk.user;

import org.joda.time.Interval;

import de.wk.date.Days;
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
	private Days days;
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
	private IntervalSet<VacationInterval> holidayIntervalSet = new IntervalSet<>();
	/**
	 * The list of intervals the user <b>don't want to be filled</b> with
	 * holidays.
	 */
	private IntervalSet<NoVacationInterval> nonHoliadayIntervals = new IntervalSet<NoVacationInterval>();

	public int getNumberOfHolidays() {
		return numberOfHolidays;
	}

	public IntervalSet<NoVacationInterval> getNonHoliadayIntervals() {
		return nonHoliadayIntervals;
	}

	public IntervalSet<VacationInterval> getHolidayIntervalSet() {
		return holidayIntervalSet;
	}
	
	public Days getDays() {
		return days;
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
	 * @param interval
	 *            The interval to be considered
	 */
	public User(String name, int numberOfHolidays, State state, Interval interval) {
		this.numberOfHolidays = numberOfHolidays;
		init(name, state, interval);
	}

	private void init(String name, State state, Interval interval) {
		this.name = name;
		this.scope = interval;
		this.days = new Days(interval);
		this.state = state;
		this.remainingNumberOfHolidays = new RemainingNumberOfHolidays(numberOfHolidays);
		holidayIntervalSet = new IntervalSet<VacationInterval>(numberOfHolidays);
	}

	/**
	 * Calculates the fix holidays for this user. While the calculation is
	 * processed, the given State is considered.
	 * 
	 * @return The user object on which the current method is invoked
	 */
	public User setHolidaysByGivenConfiguration() {
		Days days = HolidayProvider.provideBy(this.scope, this.state);
		this.days.addAll(days);
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
