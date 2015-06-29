package de.wk.date.holiday;

import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.immutable.BussUndBettag;
import de.wk.date.holiday.immutable.ChristiHimmelfahrt;
import de.wk.date.holiday.immutable.FixHoliday;
import de.wk.date.holiday.immutable.Karfreitag;
import de.wk.date.holiday.immutable.Ostermontag;
import de.wk.date.holiday.immutable.Ostersonntag;
import de.wk.date.holiday.immutable.Pfingstmontag;

public class HolidayProvider {
	public Days provideBy(Interval interval) {
		Days days = new Days();
		DateTime start = interval.getStart();
		DateTime end = interval.getEnd();
		for (int year = start.getYear(); year <= end.getYear(); year++) {
			initializeByYear(year, days);
		}
		Collections.sort(days);
		return days;
	}

	public Days provideBy(int year) {
		WKDateTime wkDateTime = new WKDateTime(year, 1, 1);
		return provideBy(new Interval(wkDateTime, wkDateTime.getJodaDateTime()
				.plusYears(1).minusDays(1)));
	}

	private void initializeByYear(int year, Days days) {
		days.add(new FixHoliday("Neujahr", year, 1, 1));
		FixHoliday firstCMD = new FixHoliday("Erster Weihnachtstag", year, 12,
				25);
		days.add(firstCMD);
		days.add(new BussUndBettag("Buss und Bettag", firstCMD));
		days.add(new FixHoliday("Zweiter Weihnachtstag", year, 12, 26));
		days.add(new FixHoliday("Maifeiertag", year, 5, 1));

		Ostersonntag ostersonntag = new Ostersonntag(year);
		days.add(ostersonntag);

		days.add(new Karfreitag(ostersonntag));
		days.add(new Ostermontag(ostersonntag));

		days.add(new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag));
		days.add(new Pfingstmontag(ostersonntag));
		days.add(new FixHoliday("Tag der Deutschen Einheit", year, 10, 3));
	}
}
