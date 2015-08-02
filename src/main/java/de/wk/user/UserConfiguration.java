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
	private Integer remainingNumberOfHolidays;
	private DateConstraint dateConstraint = new DateConstraint();
	private Integer maxLengthOfHolidayPartition = null;

	public UserConfiguration(int numberOfHolidays) {
		this.numberOfHolidays = numberOfHolidays;
		this.remainingNumberOfHolidays = numberOfHolidays;
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

	public Integer getRemainingNumberOfHolidays() {
		return remainingNumberOfHolidays;
	}

	public void setRemainingNumberOfHolidays(Integer remainingNumberOfHolidays) {
		this.remainingNumberOfHolidays = remainingNumberOfHolidays;
	}
}
