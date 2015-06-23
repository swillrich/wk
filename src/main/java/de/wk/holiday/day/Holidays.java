package de.wk.holiday.day;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

import de.wk.holiday.Month;

@SuppressWarnings("serial")
public class Holidays extends ArrayList<Holiday<?>> {

	private int year;

	public Holidays initializeByYear(int year) {
		this.year = year;
		add(new FixHoliday("Neujahr", this.year, Month.January, 1));
		add(new FixHoliday("Heiligabend", this.year, Month.December, 24));
		FixHoliday firstCMD = new FixHoliday("Erster Weihnachtstag", this.year,
				Month.December, 25);
		add(firstCMD);
		add(new BussUndBettag("Buss und Bettag", firstCMD));
		add(new FixHoliday("Zweiter Weihnachtstag", this.year, Month.December,
				26));
		add(new FixHoliday("Maifeiertag", this.year, Month.May, 1));

		Ostersonntag ostersonntag = new Ostersonntag(this.year);
		add(ostersonntag);

		add(new Karfreitag(ostersonntag));
		add(new Ostermontag(ostersonntag));

		add(new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag));
		add(new Pfingstmontag(ostersonntag));
		add(new FixHoliday("Tag der Deutschen Einheit", this.year,
				Month.October, 3));

		Collections.sort(this);
		return this;
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

	public void printWithin(DateTime from, DateTime to) {
		System.out.println("from:\t"
				+ DateTimeFormat.forPattern("dd. MMM yyyy").print(from));
		System.out.println("to:\t"
				+ DateTimeFormat.forPattern("dd. MMM yyyy").print(to));
		System.out.println();
		Duration duration = new Duration(from, to);
		int diffDays = (int) duration.getStandardDays();
		StringBuilder format = new StringBuilder();
		format.append("%-10s");
		String[] columns = new String[32];
		columns[0] = "month";
		for (int i = 1; i <= 31; i++) {
			format.append("%-5s");
			columns[i] = String.valueOf(i);
		}
		String titleBar = String.format(format.toString(), (Object[]) columns);
		System.out.println(titleBar);

		DateTime tmpDT = from;
		columns = null;
		for (int dayIndex = 0, arrIndex = 0; dayIndex <= diffDays; dayIndex++, arrIndex++) {
			if (columns == null) {
				columns = new String[32];
				columns[arrIndex] = DateTimeFormat.forPattern("MMM YY ").print(
						tmpDT);
				arrIndex = arrIndex + 1;
			}
			columns[tmpDT.getDayOfMonth()] = DateTimeFormat.forPattern("E")
					.print(tmpDT);
			if (tmpDT.getMonthOfYear() - tmpDT.plusDays(1).getMonthOfYear() != 0
					|| dayIndex == diffDays) {
				arrIndex++;
				for (int i = 1; i < columns.length; i++) {
					if (columns[i] == null
							&& (i < from.getDayOfMonth() || i > to
									.getDayOfMonth())
							&& (tmpDT.getMonthOfYear() == to.getMonthOfYear() || tmpDT
									.getMonthOfYear() == from.getMonthOfYear())) {
						columns[i] = "";
					} else if (columns[i] == null) {
						columns[i] = "-";
					}

				}
				System.out.println(String.format(format.toString(),
						(Object[]) columns));
				columns = null;
				arrIndex = -1;
			}
			tmpDT = tmpDT.plusDays(1);
		}
	}
}
