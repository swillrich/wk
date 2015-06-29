package de.wk.user;

import de.wk.algorithms.DateConstraint;

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
