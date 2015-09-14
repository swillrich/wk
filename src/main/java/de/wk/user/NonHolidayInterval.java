package de.wk.user;

import org.joda.time.Interval;

import de.wk.date.WKInterval;

public class NonHolidayInterval extends WKInterval {

	public NonHolidayInterval(Interval interval) {
		super(interval);
	}

}
