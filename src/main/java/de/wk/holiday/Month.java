package de.wk.holiday;

public enum Month {
	January(31), February(28), March(31), April(30), May(31), June(30), July(31), August(
			31), September(30), October(31), November(30), December(31);

	private int days;

	Month(int days) {
		this.days = days;
		if (name().equalsIgnoreCase("february")) {
			//check if leap year ...
		}
	}

}
