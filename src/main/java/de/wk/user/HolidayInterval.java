package de.wk.user;

import org.joda.time.Interval;

import de.wk.date.WKDateTime;
import de.wk.date.WKInterval;

public class HolidayInterval extends WKInterval {

	private boolean isMandatory = false;

	public HolidayInterval(Interval interval, boolean isMandatory) {
		super(interval);
		this.isMandatory = isMandatory;
	}

	public HolidayInterval(WKDateTime start, WKDateTime end, boolean isMandatory) {
		super(start, end);
		this.isMandatory = isMandatory;
	}

	public boolean isMandatory() {
		return isMandatory;
	}
}
