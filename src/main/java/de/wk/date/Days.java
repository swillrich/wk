package de.wk.date;

import java.util.ArrayList;

import org.joda.time.DateTime;

import de.wk.date.holiday.UserHoliday;

@SuppressWarnings("serial")
public class Days extends ArrayList<WKDateTime> {

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Holidays:\n");
		for (WKDateTime h : this) {
			b.append(h + "\n");
		}
		return b.toString();
	}

	public WKDateTime findByDate(DateTime d) {
		for (WKDateTime h : this) {
			if (h.getJodaDateTime().withTimeAtStartOfDay()
					.isEqual(d.withTimeAtStartOfDay())) {
				return h;
			}
		}
		return null;
	}

	public KindOfDay determineKindOf(DateTime dateTime) {
		KindOfDay kindOf = null;
		WKDateTime holiday = findByDate(dateTime);
		if (holiday != null) {
			if (holiday instanceof UserHoliday) {
				kindOf = KindOfDay.VACATIONDAY;
			} else {
				kindOf = KindOfDay.HOLIDAY;
			}

		} else if (dateTime.getDayOfWeek() == 7 || dateTime.getDayOfWeek() == 6) {
			kindOf = KindOfDay.WEEKEND;
		} else {
			kindOf = KindOfDay.WEEK;
		}
		return kindOf.setDateTime(dateTime);
	}

	public static enum KindOfDay {
		WEEKEND, WEEK, HOLIDAY, VACATIONDAY;
		private DateTime dateTime;

		public KindOfDay setDateTime(DateTime dateTime) {
			this.dateTime = dateTime;
			return this;
		}

		public DateTime getDateTime() {
			return dateTime;
		}
	}
}
