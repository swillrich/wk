package de.wk.algorithms;

import java.util.ArrayList;
import java.util.List;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.WKInterval;

public class Gaps extends ArrayList<Gap> {
	private List<WKInterval> partition = new ArrayList<WKInterval>();
	private Days days = null;
	private boolean verbose = false;

	public Gaps(Days days, boolean verbose) throws Exception {
		this.days = days;
		initializeIntervals();
		calculateValue();
		this.verbose = verbose;
	}

	public List<WKInterval> getPartition() {
		return partition;
	}

	/**
	 * initializes gaps and non-gaps (the letter one means partitions being
	 * filled by days being non-holidays, especially working days).
	 */
	private void initializeIntervals() throws Exception {
		WKDateTime start = null;
		boolean isWorkDay = false;
		for (int i = 0; i < days.size(); i++) {
			WKDateTime dateTime = days.get(i);
			isWorkDay = i + 1 < days.size() ? days.determineKindOf(days.get(i + 1).getDateTime()) == KindOfDay.WORKDAY
					: !isWorkDay;
			if (start == null) {
				start = dateTime;
			}
			if (isWorkDay != (days.determineKindOf(start.getDateTime()) == KindOfDay.WORKDAY)) {
				dateTime = days.get(i);
				if (!isWorkDay) {
					Gap gap = new Gap(start, dateTime);
					if (partition.size() > 0) {
						gap.setPrevInterval(partition.get(partition.size() - 1));
					}
					add(gap);
				} else {
					WKInterval wkInterval = new WKInterval(start, dateTime);
					if (size() > 0) {
						get(size() - 1).setNextInterval(wkInterval);
					}
					partition.add(wkInterval);
				}
				start = null;
			}
		}

		if (verbose) {
			printGaps();
		}
	}

	/**
	 * Prints out all gaps including all preceding and proceeding intervals
	 * being non-gaps.
	 */
	private void printGaps() {
		for (int i = 0; i < size(); i++) {
			Gap gap = get(i);
			List<WKInterval> l = new ArrayList<WKInterval>();
			if (gap.getPrevInterval() != null) {
				l.add(gap.getPrevInterval());
			}
			l.add(gap);
			if (gap.getNextInterval() != null && i + 1 == size()) {
				l.add(gap.getNextInterval());
			}
			for (WKInterval wki : l) {
				System.out.println(wki.toString());
			}
		}
	}

	private void calculateValue() {
		int newValue;
		for (Gap gap : this) {
			newValue = gap.getSize() + gap.getPrevInterval().getSize() + gap.getNextInterval().getSize();
			gap.setValue(newValue);
		}
	}
}
