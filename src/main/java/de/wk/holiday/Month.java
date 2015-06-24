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
	
	public static Month getMonthByInt(int monthNumber){
		switch (monthNumber) {
		case 1:  return Month.January;
		case 2:  return Month.February;
		case 3:  return Month.March;
		case 4:  return Month.April;
		case 5:  return Month.May;
		case 6:  return Month.June;
		case 7:  return Month.July;
		case 8:  return Month.August;
		case 9:  return Month.September;
		case 10: return Month.October;
		case 11: return Month.November;
		case 12: return Month.December;
		default:
			return null;
		}
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
