package de.wk.algorithms;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import de.wk.Log;
import de.wk.domain.WKDateTime;
import de.wk.domain.WKDateTime.KindOfDay;
import de.wk.domain.holiday.Holidays;
import de.wk.domain.holiday.VariableHoliday;
import de.wk.domain.user.User;

/**
 * This algorithm invoked firstly. He fills the gaps between weekend days and
 * holidays. We just consider tuesdays, wednesdays and thursdays because the
 * others do not cause a gap.
 *
 */
public class FillGapsAlgorithm implements HolidayCalculatorAlgorithm {

	private User user;
	private Holidays holidays;
	private int numberOfHolidays;

	@Override
	public void calculate(User user) {
		init(user);
		if (this.numberOfHolidays > 0) {
			fillGaps();
		}
	}

	private void fillGaps() {
		// copy list to avoid ConcurrentModificationException
		ArrayList<WKDateTime> copy = new ArrayList<WKDateTime>(this.user.getHolidays());
		for (WKDateTime holiday : copy) {
			// TUESDAY
			if (holiday.getDayOfWeek() == 2) {
				addNewHoliday(holiday, false);
				// WEDNESDAY
			} else if (holiday.getDayOfWeek() == 3) {
				if (this.numberOfHolidays >= 2) {
					WKDateTime tmp = addNewHoliday(holiday, true);
					addNewHoliday(tmp, true);
				}
				// THURSDAY
			} else if (holiday.getDayOfWeek() == 4) {
				addNewHoliday(holiday, true);
			}
		}
	}

	/**
	 * 
	 * @param currentHoliday
	 *            holiday that cause the gap
	 * @param isPlusDay
	 *            true, then add a day after the currentHoliday false, then add
	 *            a day before the currentHoliday
	 * @return the newly added holiday
	 */
	private WKDateTime addNewHoliday(WKDateTime currentHoliday, boolean isPlusDay) {
		int x = isPlusDay ? 1 : -1;
		// point to new holiday to take
		DateTime tmp = currentHoliday.getJodaDateTime().plusDays(x);
		KindOfDay kindOf = user.getHolidays().determineKindOf(tmp);
		// just consider days, that is neither a weekend day nor holiday
		if (kindOf != KindOfDay.WEEKEND && kindOf != KindOfDay.HOLIDAY) {
			VariableHoliday newHoliday = new VariableHoliday("Urlaubstag", tmp.getYear(), tmp.getMonthOfYear(),
					tmp.getDayOfMonth());
			this.holidays.add(newHoliday);
			this.numberOfHolidays--;
			Log.out("Added " + DateTimeFormat.forPattern("dd. MMM yyyy").print(tmp));
			return newHoliday;
		}
		return null;
	}

	private void init(User user) {
		this.user = user;
		this.numberOfHolidays = this.user.getRemainingHolidays().get();
		this.holidays = user.getHolidays();
	}
}
