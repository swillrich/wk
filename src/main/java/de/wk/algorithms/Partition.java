package de.wk.algorithms;

import de.wk.date.Days;
import de.wk.date.WKDateTime;

/**
 * This class represents a partition with days (and inherits from days) whose
 * interval between previous or next days is zero.
 */
@SuppressWarnings("serial")
public class Partition extends Days {
	/**
	 * Max length of this partition.
	 */
	private int maxLength;

	public Partition(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public boolean add(WKDateTime e) {
		if (size() >= maxLength) {
			return false;
		}
		return super.add(e);
	}

	@Override
	public void add(int index, WKDateTime element) {
		// No implementation. Because missing possibility to handle constraint
		// violation with respect to maxlength.
	}
}
