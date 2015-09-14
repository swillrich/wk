package de.wk.user;

import org.joda.time.Interval;

import de.wk.date.WKDateTime;
import de.wk.date.WKInterval;

public class HolidayInterval extends WKInterval {

	public HolidayInterval(Interval interval) {
		super(interval);
	}

	public HolidayInterval(WKDateTime start, WKDateTime end) {
		super(start, end);
	}

}
