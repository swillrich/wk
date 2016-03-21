package de.wk.algorithms;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.holiday.VacationDay;
import de.wk.user.User;
import de.wk.user.VacationInterval;

public class FillPriorityIntervalsAlgorithm implements HolidayCalculatorAlgorithm {

	@Override
	public void calculate(User user) throws Exception {
		Iterator<VacationInterval> iterator = user.getHolidayIntervalSet().toIterator();
		while (iterator.hasNext()) {
			Interval interval = iterator.next().getInterval();
			for (DateTime dateTime = interval.getStart(); (interval.contains(dateTime)
					|| dateTime.compareTo(interval.getEnd()) == 0)
					&& user.getRemainingHolidays().stillAvailable(); dateTime = dateTime.plusDays(1)) {
				KindOfDay kindOf = user.getDays().determineKindOf(dateTime);
				if (kindOf == KindOfDay.WORKDAY) {
					VacationDay variableHoliday = new VacationDay("being preferred holiday", dateTime);
					user.getDays().add(variableHoliday);
					user.getRemainingHolidays().decrement();
				}
			}
		}
	}

}
