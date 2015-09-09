package de.wk.user;

import java.util.ArrayList;
import java.util.List;

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
	private RemainingNumberOfHoldiay remainingNumberOfHolidays;
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
	private List<Interval> preferredHolidayIntervals = new ArrayList<Interval>();
	/**
	 * The list of intervals the user <b>don't want to be filled</b> with
	 * holidays.
	 */
	private List<Interval> noHolidayIntervals = new ArrayList<Interval>();

	public int getNumberOfHolidays() {
		return numberOfHolidays;
	}

	public List<Interval> getPreferredHolidayIntervals() {
		return preferredHolidayIntervals;
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
		this.name = name;
		this.holidays = new Days();
		this.state = state;
		this.numberOfHolidays = numberOfHolidays;
		this.remainingNumberOfHolidays = new RemainingNumberOfHoldiay(numberOfHolidays);
		this.scope = new Interval(start, end);
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
		this.name = name;
		this.holidays = new Days();
		this.state = state;
		this.numberOfHolidays = numberOfHolidays;
		this.remainingNumberOfHolidays = new RemainingNumberOfHoldiay(numberOfHolidays);
		this.scope = new Interval(new WKDateTime(year, 1, 1), new WKDateTime(year, 12, 31));
	}

	/**
	 * Calculates the fix holidays for this user. While the calculation is
	 * processed, the given State is considered.
	 * 
	 * @return The user object on which the current method is invoked
	 */
	public User setHolidaysByGivenConfiguration() {
		Days days = new HolidayProvider().provideBy(this.scope, this.state);
		this.holidays.addAll(days);
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Interval getScope() {
		return scope;
	}

	public List<Interval> getNoHolidayIntervals() {
		return noHolidayIntervals;
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
	public RemainingNumberOfHoldiay getRemainingHolidays() {
		return remainingNumberOfHolidays;
	}

	/**
	 * This class handles the incrementation/decrementation of the number of
	 * remaining holidays. The class is necessary because the number of
	 * remaining holidays must be accessible from all over the program.
	 * Furthermore, value changes have to lead to changes relating the contained
	 * int32 representing the number of remaining holidays.
	 */
	public class RemainingNumberOfHoldiay {
		private int remainingNumber;

		public RemainingNumberOfHoldiay(int numberOfHolidays) {
			this.remainingNumber = User.this.numberOfHolidays;
		}

		/**
		 * Decrements the current number of remaining holidays.
		 * 
		 * @return Is true if the number of holidays would been smaller after
		 *         method run. False otherwise and if the current number of
		 *         remaining holidays has reached the smallest possible value.
		 */
		public boolean decrement() {
			if (0 >= this.remainingNumber) {
				return false;
			} else {
				this.remainingNumber = this.remainingNumber - 1;
				return true;
			}
		}

		/**
		 * Increments the current number of remaining holidays.
		 * 
		 * @return Is true if the number of holidays would been bigger after
		 *         method run. False otherwise and if the current number of
		 *         remaining holidays has reached his maximum (that is the total
		 *         number of holidays being initialized initially).
		 */
		public boolean increment() {
			if (User.this.numberOfHolidays >= this.remainingNumber) {
				return false;
			} else {
				this.remainingNumber = this.remainingNumber + 1;
				return true;
			}
		}

		/**
		 * Resets the current number of holiday to the initial value referred to
		 * as total number of holidays.
		 */
		public void reset() {
			this.remainingNumber = User.this.numberOfHolidays;
		}

		/**
		 * @return The current number of remaining holidays.
		 */
		public int get() {
			return remainingNumber;
		}

		/**
		 * @return Is true if holidays are still available to take.
		 */
		public boolean stillAvailable() {
			return remainingNumber > 0;
		}
	}
}
