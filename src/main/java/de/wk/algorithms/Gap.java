package de.wk.algorithms;

import de.wk.date.WKDateTime;
import de.wk.date.WKInterval;

public class Gap extends WKInterval {

	private WKInterval prevInterval;
	private WKInterval nextInterval;

	public Gap(WKInterval interval) {
		super(interval.getInterval());
	}

	public Gap(WKDateTime start, WKDateTime end) {
		super(start, end);
	}

	public int getWeight() {
		return -1;
	}

	public void setNextInterval(WKInterval nextInterval) {
		this.nextInterval = nextInterval;
	}

	public void setPrevInterval(WKInterval prevInterval) {
		this.prevInterval = prevInterval;
	}

	public WKInterval getNextInterval() {
		return nextInterval;
	}

	public WKInterval getPrevInterval() {
		return prevInterval;
	}

}
