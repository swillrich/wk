package de.wk.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class DateConstraint {
	private DateTime startingDay;
	private DateTime endDay;
	private List<Interval> intervalList = new ArrayList<Interval>();

	public void setStartingDay(DateTime startingDay) {
		this.startingDay = startingDay;
	}

	public DateTime getEndDay() {
		return endDay;
	}

	public List<Interval> getIntervalList() {
		return intervalList;
	}
}
