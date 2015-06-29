package de.wk.algorithms;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.User;
import de.wk.date.Days.KindOfDay;
import de.wk.date.holiday.UserHoliday;
import de.wk.util.HolidayPriorityQueue;

public class BiggestPartitionAlgorithm implements Algorithm {

	@Override
	public void calculate(User user) {
		HolidayPriorityQueue queue = new HolidayPriorityQueue(
				user.getHolidays());

		for (Integer remainNumberOfHolidays = user.getNumberOfHolidays(); remainNumberOfHolidays > 0
				&& !queue.isEmpty();) {
			Interval interval = queue.poll();
			if (interval.toDuration().getStandardDays() > 1) {
				for (int dayIndex = 1; dayIndex <= interval.toDuration()
						.getStandardDays() && remainNumberOfHolidays > 0; dayIndex++) {
					DateTime date = interval.getStart();
					DateTime plusDays = date.plusDays(dayIndex);
					KindOfDay kindOf = user.getHolidays().determineKindOf(
							plusDays);
					if (kindOf != KindOfDay.WEEKEND) {
						UserHoliday userHoliday = new UserHoliday("Vacation",
								plusDays);
						user.getHolidays().add(userHoliday);
						remainNumberOfHolidays = remainNumberOfHolidays - 1;
					}
				}
			}
		}
	}
}
