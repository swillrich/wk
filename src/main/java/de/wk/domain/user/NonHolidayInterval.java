package de.wk.domain.user;

import org.joda.time.Interval;

import de.wk.domain.WKInterval;

public class NonHolidayInterval extends WKInterval {

	public NonHolidayInterval(Interval interval) {
		super(interval);
	}

}
