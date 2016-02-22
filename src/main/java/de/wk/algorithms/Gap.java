package de.wk.algorithms;

import de.wk.date.WKDateTime;
import de.wk.date.WKInterval;

public class Gap extends WKInterval {

	private WKInterval prevInterval;
	private WKInterval nextInterval;
	private int value;

	public Gap(WKInterval interval) {
		super(interval.getInterval());
		this.value = -1;
	}

	public Gap(WKDateTime start, WKDateTime end) {
		super(start, end);
		this.value = -1;
	}

	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = (value <= 0) ? -1 : value;
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
