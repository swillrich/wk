package de.wk.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class DateConstraint {
	private DateTime startingDay;
	private DateTime untilDay;
	private List<Interval> intervalList = new ArrayList<Interval>();

	public void setStartingDay(DateTime startingDay) {
		this.startingDay = startingDay;
	}

	public void setUntilDay(DateTime untilDay) {
		this.untilDay = untilDay;
	}

	public List<Interval> getIntervalList() {
		return intervalList;
	}
}
