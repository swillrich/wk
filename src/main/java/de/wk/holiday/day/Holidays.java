package de.wk.holiday.day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.wk.holiday.Month;

public class Holidays extends ArrayList<Holiday<?>> {

	private int year;

	public Holidays(int year) {
		this.year = year;
		add(new FixHoliday("Neujahr", year, Month.January, 1));
		add(new FixHoliday("Heiligabend", year, Month.December, 24));
		add(new FixHoliday("Erster Weihnachtstag", year, Month.December, 25));
		add(new FixHoliday("Zweiter Weihnachtstag", year, Month.December, 26));
		add(new FixHoliday("Maifeiertag", year, Month.May, 1));

		Ostersonntag ostersonntag = new Ostersonntag(year);
		add(ostersonntag);

		add(new Karfreitag(ostersonntag));
		add(new Ostermontag(ostersonntag));

		add(new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag));
		add(new Pfingstmontag(ostersonntag));
		add(new FixHoliday("Tag der Deutschen Einheit", year, Month.October, 3));

		Collections.sort(this);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Holidays:\n");
		for (Holiday h : this) {
			b.append(h + "\n");
		}
		return b.toString();
	}
}
