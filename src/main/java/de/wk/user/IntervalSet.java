package de.wk.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.wk.Log;
import de.wk.date.WKInterval;

/**
 * The role of this class is to manage holidays or non-holidays intervals. <br/>
 * The class considers the total number of available holidays with the length of
 * all previously added interval length.
 */
public class IntervalSet<KindOf extends WKInterval> {

	private Integer numberOfDaysRestriction;

	public IntervalSet(int numberOfDaysRestriction) {
		this.numberOfDaysRestriction = numberOfDaysRestriction;
	}

	public IntervalSet() {
	}

	private List<KindOf> intervals = new ArrayList<KindOf>();

	/**
	 * Adds a new preferred interval as WKInterval. The method compares the
	 * total number of available holidays to the number of days resulting from
	 * all previously added intervals.
	 * 
	 * @param interval
	 *            the kind of the interval. If the interval is of type
	 *            NonHolidayInterval, the interval can be added immediately.
	 *            Otherwise a test must be execute due to total number of
	 *            holiday restrictions.
	 * 
	 * 
	 * @return Is true if the current days length of already added intervals and
	 *         the length of the new given interval, which should be added, is
	 *         smaller than the total number of holidays. Otherwise false, that
	 *         is the already existing intervals in sum plus the days length of
	 *         the new interval is bigger than all holidays together the user
	 *         could generally take.
	 */
	public boolean add(KindOf interval) {
		if (numberOfDaysRestriction == null) {
			return this.intervals.add(interval);
		} else {
			long newIntervalDaysLength = interval.getInterval().toDuration().getStandardDays();
			long totalNumberOfDaysLength = 0;
			for (WKInterval wkInterval : this.intervals) {
				long currentIntervalDaysLengte = wkInterval.getInterval().toDuration().getStandardDays();
				totalNumberOfDaysLength = totalNumberOfDaysLength + currentIntervalDaysLengte;
			}
			if (totalNumberOfDaysLength + newIntervalDaysLength > numberOfDaysRestriction) {
				Log.out("Could not add this interval, because adding" + " would lead to size exceeding. There are "
						+ totalNumberOfDaysLength + " of " + this.numberOfDaysRestriction
						+ " days claimed, so that his interval with " + newIntervalDaysLength
						+ " is not allowed to be added.");
				return false;
			} else {
				return this.intervals.add(interval);
			}
		}
	}

	/**
	 * Returns all the added intervals being preferred to be filled with
	 * holidays as iterator. <br/>
	 * <br/>
	 * An iterator is provided to avoid unchecked adding of new intervals, <b>if
	 * a maximum number of days is given while construction this object</b>.
	 * Intervals being planned to add must be checked, so that no size exceeding
	 * results. A size exceeding results if the total number of holidays is
	 * exceeded by the sum of days over all given intervals.
	 */
	public Iterator<KindOf> toIterator() {
		return this.intervals.iterator();
	}
}
