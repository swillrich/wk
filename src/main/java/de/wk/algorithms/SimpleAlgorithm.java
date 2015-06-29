package de.wk.algorithms;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.Days.KindOfDay;
import de.wk.date.holiday.UserHoliday;
import de.wk.user.User;
import de.wk.util.PriorityQueue;

public class SimpleAlgorithm implements Algorithm {

	private final int NUMBER_OF_PRIORITIES = 3;
	private PriorityQueue<WKDateTime> holidaysByPriority;
	private User user;
	private Days holidays;
	private int numberOfHolidays;

	@Override
	public void calculate(User user) {
		init(user);
		if (this.numberOfHolidays > 0) {
			prioritizeHolidays();
			fillGaps();
		}
		System.out.println(this.numberOfHolidays + " days remaining.");
		this.user.getUserConfiguration().setRemainingNumberOfHolidays(
				this.numberOfHolidays);
	}

	private void fillGaps() {
		// start with highest priority 2
		for (int i = 2; i >= 0; i--) {
			ArrayList<WKDateTime> currentPriority = this.holidaysByPriority
					.getAllElementsByPriority(i);
			for (WKDateTime currentHoliday : currentPriority) {
				if (this.numberOfHolidays == 0) {
					break;
				}
				switch (currentHoliday.getDayOfWeek()) {
				//case 1:
				case 4:
					//case 7:
					addNewHoliday(currentHoliday, true);
					break;
				case 2:
					//case 5:
					//case 6:
					addNewHoliday(currentHoliday, false);
					break;
				case 3:
					if (this.numberOfHolidays >= 2) {
						WKDateTime tmp = addNewHoliday(currentHoliday, true);
						addNewHoliday(tmp, true);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private WKDateTime addNewHoliday(WKDateTime currentHoliday,
			boolean isPlusDay) {
		int x = isPlusDay ? 1 : -1;
		DateTime tmp = currentHoliday.getJodaDateTime().plusDays(x);
		KindOfDay kindOf = user.getHolidays().determineKindOf(tmp);
		if (kindOf != KindOfDay.WEEKEND && kindOf != KindOfDay.HOLIDAY) {
			UserHoliday newHoliday = new UserHoliday("Urlaubstag",
					tmp.getYear(), tmp.getMonthOfYear(), tmp.getDayOfMonth());
			this.holidays.add(newHoliday);
			this.numberOfHolidays--;
			System.out.println("Added "
					+ DateTimeFormat.forPattern("dd. MMM yyyy").print(tmp));
			return newHoliday;
		}
		return null;
	}

	private void prioritizeHolidays() {
		Days holidays = this.user.getHolidays();
		for (WKDateTime holiday : holidays) {
			if (holiday.getDayOfWeek() == 6 || holiday.getDayOfWeek() == 7) {
				this.holidaysByPriority.addElementWithPriority(0, holiday);
			} else if (holiday.getDayOfWeek() == 1
					|| holiday.getDayOfWeek() == 5) {
				this.holidaysByPriority.addElementWithPriority(1, holiday);
			} else {
				this.holidaysByPriority.addElementWithPriority(2, holiday);
			}
		}
	}

	private void init(User user) {
		this.holidaysByPriority = new PriorityQueue<WKDateTime>(
				NUMBER_OF_PRIORITIES);
		this.user = user;
		this.numberOfHolidays = this.user.getUserConfiguration()
				.getNumberOfHolidays();
		this.holidays = user.getHolidays();
	}
}
