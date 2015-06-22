package de.wk.holiday;

import org.joda.time.DateTime;

public enum Month {
	January(31), February(28), March(31), April(30), May(31), June(30), July(31), August(
			31), September(30), October(31), November(30), December(31);

	private int days;

	Month(int days) {
		int currentYear = new DateTime().getYear();
		if (name().equalsIgnoreCase("february")
				&& (currentYear % 4 == 0 && (currentYear % 100 != 0 || currentYear % 400 == 0))) {
			days += 1;
		}
		this.days = days;
	}

	public int getDays() {
		return days;
	}

	public static int numberOfMonth(Month month) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i] == month) {
				return i + 1;
			}
		}
		return -1;
	}

}
