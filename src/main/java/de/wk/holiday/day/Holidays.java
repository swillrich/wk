package de.wk.holiday.day;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

import de.wk.holiday.Month;
import de.wk.holiday.UserHoliday;

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

	public Holiday<?> findByDate(DateTime d) {
		for (Holiday<?> h : this) {
			if (h.getDate().withTimeAtStartOfDay()
					.isEqual(d.withTimeAtStartOfDay())) {
				return h;
			}
		}
		return null;
	}

	public KindOf determineKindOf(DateTime dateTime) {
		KindOf kindOf = null;
		Holiday<?> holiday = findByDate(dateTime);
		if (holiday != null) {
			if (holiday instanceof UserHoliday) {
				kindOf = KindOf.VACATIONDAY;
			} else {
				kindOf = KindOf.HOLIDAY;
			}

		} else if (dateTime.getDayOfWeek() == 7 || dateTime.getDayOfWeek() == 6) {
			kindOf = KindOf.WEEKEND;
		} else {
			kindOf = KindOf.WEEK;
		}
		return kindOf.setDateTime(dateTime);
	}

	public static enum KindOf {
		WEEKEND, WEEK, HOLIDAY, VACATIONDAY;
		private DateTime dateTime;

		public KindOf setDateTime(DateTime dateTime) {
			this.dateTime = dateTime;
			return this;
		}

		public DateTime getDateTime() {
			return dateTime;
		}
	}
}
