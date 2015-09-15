package de.wk.domain.user;

/**
 * This class handles the incrementation/decrementation of the number of
 * remaining holidays. The class is necessary because the number of
 * remaining holidays must be accessible from all over the program.
 * Furthermore, value changes have to lead to changes relating the contained
 * int32 representing the number of remaining holidays.
 */
public class RemainingNumberOfHolidays {
	private int remainingNumber;
	private final int numberOfHolidays;

	public RemainingNumberOfHolidays(int numberOfHolidays) {
		this.remainingNumber = numberOfHolidays;
		this.numberOfHolidays = numberOfHolidays;
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
		if (this.numberOfHolidays >= this.remainingNumber) {
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
		this.remainingNumber = this.numberOfHolidays;
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