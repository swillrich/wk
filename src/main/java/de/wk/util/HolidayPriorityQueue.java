package de.wk.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.joda.time.Interval;

import de.wk.Log;
import de.wk.date.Days;

/**
 * This priority queue contains all Intervals being between all holidays. The
 * priority characteristic is the increasing length of the Interval. The longest
 * Interval is the last element.
 */
@SuppressWarnings("serial")
public class HolidayPriorityQueue extends PriorityQueue<Interval> {
	/**
	 * The priority queue will be initialized by a list of days (which are
	 * resorted by date), whose Intervals will be calculated. One Interval is
	 * composed of a arbitrary holiday and the direct next holiday.
	 */
	public HolidayPriorityQueue(Days days) {
		super(new Comparator<Interval>() {
			@Override
			public int compare(Interval o1, Interval o2) {
				return o1.toDuration().compareTo(o2.toDuration());
			}
		});
		Collections.sort(days);
		for (int i = 0; i + 1 < days.size(); i++) {
			Interval interval = new Interval(days.get(i), days.get(i + 1));
			add(interval);
		}
	}

	/**
	 * Prints out all Intervals.<br>
	 * Because this is a queue, printing out all Intervals leads to a empty
	 * queue.
	 */
	public void print() {
		for (Interval h : this) {
			Log.out(h.getStart() + " ------------------>  "
					+ h.getEnd());
		}
		Log.newLine();
	}
}
