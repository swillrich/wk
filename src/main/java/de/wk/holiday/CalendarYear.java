package de.wk.holiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.wk.holiday.day.ChristiHimmelfahrt;
import de.wk.holiday.day.FixHoliday;
import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Karfreitag;
import de.wk.holiday.day.Ostermontag;
import de.wk.holiday.day.Ostersonntag;
import de.wk.holiday.day.Pfingstmontag;

public class CalendarYear {

	public CalendarYear(int year) {
		computeHolidays(year);

		System.out.println("Holidays:");
		for (Holiday h : computeHolidays(year)) {
			System.out.println(h);
		}
	}

	private List<Holiday<?>> computeHolidays(int year) {
		List<Holiday<?>> days = new ArrayList<Holiday<?>>();
		days.add(new FixHoliday("Neujahr", year, Month.January, 1));
		days.add(new FixHoliday("Heiligabend", year, Month.December, 24));
		days.add(new FixHoliday("Erster Weihnachtstag", year, Month.December,
				25));
		days.add(new FixHoliday("Zweiter Weihnachtstag", year, Month.December,
				26));
		days.add(new FixHoliday("Maifeiertag", year, Month.May, 1));

		Ostersonntag ostersonntag = new Ostersonntag(year);
		days.add(ostersonntag);

		days.add(new Karfreitag(ostersonntag));
		days.add(new Ostermontag(ostersonntag));

		days.add(new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag));
		days.add(new Pfingstmontag(ostersonntag));
		days.add(new FixHoliday("Tag der Deutschen Einheit", year,
				Month.October, 3));

		Collections.sort(days);
		return days;
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
