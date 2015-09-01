package de.wk.user;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.ReadableDateTime;

/**
 * Used for specifying date constraints to create an interval which is
 * restricted by upward and downward delimitations to be filled with holidays.
 * No (calculated) holiday is allowed to be out of range. The interval can also
 * be created without giving either an upper or lower bound.<br>
 * Moreover it can be used a Interval list to add arbitrary intervals, whereby
 * the added intervals must be within the lower and upper bound. These intervals
 * have to be filled with holidays.ays.
 * 
 * This class is also a member of the UserConfiguration class and can be used to
 * configure user-preferred intervals.
 */
public class DateConstraint {
	private ReadableDateTime startingDay;
	private ReadableDateTime endDay;
	private List<Interval> intervalList = new ArrayList<Interval>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean add(Interval e) {
			if (startingDay != null) {
				if (e.getStart().compareTo(startingDay) < 0) {
					return false;
				}
			}
			if (endDay != null) {
				if (e.getEnd().compareTo(endDay) > 0) {
					return false;
				}
			}
			return super.add(e);
		};
	};

	/**
	 * Configure the first day of the interval.
	 */
	public void setStartingDay(ReadableDateTime startingDay) {
		this.startingDay = startingDay;
	}

	/**
	 * Configure the last day of the interval.
	 */
	public void setEndDay(ReadableDateTime endDay) {
		this.endDay = endDay;
	}

	/**
	 * Returns the list containing all additional intervals. These Intervals
	 * will getting filled with priority as long as remaining number of holidays
	 * are available.
	 */
	public List<Interval> getIntervalList() {
		return intervalList;
	}
}
