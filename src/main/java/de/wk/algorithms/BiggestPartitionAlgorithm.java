package de.wk.algorithms;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.holiday.VariableHoliday;
import de.wk.user.User;
import de.wk.user.UserConfiguration.RemainingNumberOfHolidays;
import de.wk.util.HolidayPriorityQueue;

/**
 * This class represents the algorithm implementation for calculating the
 * biggest holiday partition within a given range/interval. The biggest holiday
 * partition may be encompass an upper bound so that more than one coherent
 * partition could be created.
 */
public class BiggestPartitionAlgorithm implements HolidayCalculatorAlgorithm {

	@Override
	public void calculate(User user) {
		HolidayPriorityQueue queue = new HolidayPriorityQueue(
				user.getHolidays());

		for (RemainingNumberOfHolidays remaining = user.getUserConfiguration()
				.getRemainingNumberOfHolidays(); remaining.get() > 0
				&& !queue.isEmpty();) {
			Interval interval = queue.poll();
			for (int dayIndex = 1; dayIndex <= interval.toDuration()
					.getStandardDays() && remaining.get() > 0; dayIndex++) {
				DateTime startDate = interval.getStart();
				DateTime plusDays = startDate.plusDays(dayIndex);
				KindOfDay kindOf = user.getHolidays().determineKindOf(plusDays);
				if (kindOf != KindOfDay.WEEKEND && kindOf != KindOfDay.HOLIDAY) {
					VariableHoliday userHoliday = new VariableHoliday(
							"Vacation", plusDays);
					user.getHolidays().add(userHoliday);
					remaining.decrement();
				}
			}
		}
	}
}
