package de.wk.user;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Used for specifying date constraints to create an interval which is
 * restricted by upward and downward delimitations. The interval can also be
 * created without giving either an upper or lower bound. <br>
 * Moreover it can be used a Interval list to add arbitrary intervals, whereby
 * the added intervals must be within the lower and upper bound.
 * 
 * This class is also a member of the UserConfiguration class and can be used to
 * configure user-preferred intervals.
 */
public class DateConstraint {
	private DateTime startingDay;
	private DateTime endDay;
	private List<Interval> intervalList = new ArrayList<Interval>();

	/**
	 * Configure the first day of the interval.
	 */
	public void setStartingDay(DateTime startingDay) {
		this.startingDay = startingDay;
	}

	/**
	 * Configure the first day of the interval.
	 */
	public DateTime getEndDay() {
		return endDay;
	}

	/**
	 * Returns the list containing all additional intervals.
	 */
	public List<Interval> getIntervalList() {
		return intervalList;
	}
}
