package de.wk.algorithms;

import java.util.Collections;
import java.util.Comparator;

import org.joda.time.DateTime;

import de.wk.date.WKInterval;
import de.wk.date.holiday.VacationDay;
import de.wk.user.User;

public class FillWithRemainingHolidaysAlgorithm implements HolidayCalculatorAlgorithm {

	@Override
	public void calculate(User user) throws Exception {
		fillWithRemainingHolidays(user);
	}

	private void fillWithRemainingHolidays(User user) throws Exception {
		Gaps gaps = new Gaps(user.getDays(), false);

		WKInterval min = Collections.min(gaps.getPartition(), new Comparator<WKInterval>() {

			@Override
			public int compare(WKInterval arg0, WKInterval arg1) {
				if (arg0.getSize() <= 2 && arg1.getSize() > 2) {
					return 1;
				} else if (arg0.getSize() > 2 && arg1.getSize() <= 2) {
					return -1;
				} else if (arg0.getSize() <= 2 && arg1.getSize() <= 2) {
					return 0;
				} else {
					return Integer.compare(arg0.getSize(), arg1.getSize());
				}
			}
		});

		if (min != null) {
			DateTime start = min.getStart();
			DateTime end = min.getEnd();
			for (; user.getRemainingHolidays().get() > 0; user.getRemainingHolidays().decrement()) {
				DateTime minus1Day = start.minusDays(1);
				DateTime plus1Day = end.plusDays(1);
				VacationDay vacationDay = null;
				if (minus1Day.compareTo(user.getDays().get(0)) >= 0) {
					vacationDay = new VacationDay("calculated vacation day", minus1Day);
					start = minus1Day;
				} else if (plus1Day.compareTo(user.getDays().get(user.getDays().size() - 1)) <= 0) {
					vacationDay = new VacationDay("calculated vacation day", plus1Day);
					end = plus1Day;
				}
				user.getDays().replaceDay(vacationDay);
			}
		}
	}

}
