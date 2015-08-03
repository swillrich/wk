package de.wk.user;

/**
 * The user configuration is user-specific and is a component of the user. It
 * consists of the total number of holiday the user can take (immutable, only
 * through initializing), the remaining number of holidays (e.g. after
 * calculating some holidays), the date constraints (see class DateConstraint,
 * for building a partition, in which the holidays should be located), and the
 * information, how big the maximal partition must be.
 */
public class UserConfiguration {
	private final int numberOfHolidays;
	private RemainingNumberOfHolidays remainingNumberOfHolidays;
	private DateConstraint dateConstraint = new DateConstraint();
	private Integer maxLengthOfHolidayPartition = null;

	/**
	 * This class handles the change of the number of holidays. A new type was
	 * created to operate on the same reference basically / globally.
	 */
	public class RemainingNumberOfHolidays {
		private int remainingNumberOfHolidays;

		public RemainingNumberOfHolidays(int remainingNumberOfHolidays) {
			this.remainingNumberOfHolidays = remainingNumberOfHolidays;
		}

		/**
		 * Resets the number of remaining holidays.
		 */
		public void reset(int numberOfRemainingHolidays) {
			this.remainingNumberOfHolidays = numberOfRemainingHolidays;
		}

		/**
		 * Decrements the current number of remaining holidays.
		 */
		public void decrement() {
			this.remainingNumberOfHolidays = remainingNumberOfHolidays - 1;
		}

		/**
		 * Increments the current number of remaining holidays.
		 */
		public void increment() {
			this.remainingNumberOfHolidays = remainingNumberOfHolidays + 1;
		}

		/**
		 * Returns the current state (that is the current number of remaining
		 * holidays).
		 */
		public int get() {
			return remainingNumberOfHolidays;
		}
	}

	public UserConfiguration(int numberOfHolidays) {
		this.numberOfHolidays = numberOfHolidays;
		this.remainingNumberOfHolidays = new RemainingNumberOfHolidays(
				numberOfHolidays);
	}

	public Integer getMaxLengthOfHolidayPartition() {
		return maxLengthOfHolidayPartition;
	}

	public void setMaxLengthOfHolidayPartition(int maxLengthOfHolidayPartition) {
		this.maxLengthOfHolidayPartition = maxLengthOfHolidayPartition;
	}

	public int getNumberOfHolidays() {
		return numberOfHolidays;
	}

	public DateConstraint getDateConstraint() {
		return dateConstraint;
	}

	public RemainingNumberOfHolidays getRemainingNumberOfHolidays() {
		return remainingNumberOfHolidays;
	}
}
