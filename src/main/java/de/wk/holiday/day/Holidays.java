package de.wk.holiday.day;

import java.util.ArrayList;
import java.util.Collections;

import de.wk.holiday.Month;

@SuppressWarnings("serial")
public class Holidays extends ArrayList<Holiday<?>> {

	private int year;

	public Holidays(int year) {
		this.year = year;
		add(new FixHoliday("Neujahr", this.year, Month.January, 1));
		add(new FixHoliday("Heiligabend", this.year, Month.December, 24));
		add(new FixHoliday("Erster Weihnachtstag", this.year, Month.December, 25));
		add(new FixHoliday("Zweiter Weihnachtstag", this.year, Month.December, 26));
		add(new FixHoliday("Maifeiertag", this.year, Month.May, 1));

		Ostersonntag ostersonntag = new Ostersonntag(this.year);
		add(ostersonntag);

		add(new Karfreitag(ostersonntag));
		add(new Ostermontag(ostersonntag));

		add(new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag));
		add(new Pfingstmontag(ostersonntag));
		add(new FixHoliday("Tag der Deutschen Einheit", this.year, Month.October, 3));
		add(new BussUndBettag(this.year));

		Collections.sort(this);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Holidays:\n");
		for (Holiday<?> h : this) {
			b.append(h + "\n");
		}
		return b.toString();
	}
}
