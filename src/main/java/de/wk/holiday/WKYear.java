package de.wk.holiday;

import java.util.ArrayList;
import java.util.List;

import de.wk.holiday.day.FixHoliday;
import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Ostersonntag;

public class WKYear {

	public WKYear(int year) {
		computeHolidays(year);
		printYear();
	}

	private void computeHolidays(int year) {
		List<Holiday<?>> days = new ArrayList<Holiday<?>>();
		days.add(new FixHoliday("Heiligabend", year, Month.December, 24));
		days.add(new Ostersonntag(year));
		
		for (Holiday h : days) {
			System.out.println(h);
		}
	}

	public void printYear() {
		StringBuilder formatBuilder = new StringBuilder();
		for (int i = 0; i < Month.values().length; i++) {
			formatBuilder.append("%-10s");
		}
		String format = String.format(formatBuilder.toString(), Month.values());

		String underline = "";
		for (int i = 0; i < format.length(); i++) {
			underline += "=";
		}

		System.out.println(format + "\n" + underline);

		for (int i = 1; i <= 31; i++) {
			String[] days = new String[Month.values().length];
			for (int currentMonthIndex = 0; currentMonthIndex < days.length; currentMonthIndex++) {
				if (Month.values()[currentMonthIndex].getDays() >= i) {
					days[currentMonthIndex] = String.valueOf(i);
				} else {
					days[currentMonthIndex] = "-";
				}
			}
			System.out.println(String.format(formatBuilder.toString(), days));
		}
	}
}
