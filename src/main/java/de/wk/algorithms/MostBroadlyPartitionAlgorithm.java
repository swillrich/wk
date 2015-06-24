package de.wk.algorithms;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import de.wk.User;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.UserHoliday;
import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Holidays;

public class MostBroadlyPartitionAlgorithm implements Algorithm {

	@Override
	public Holidays calculate(CalendarYear year, User user) {
		HolidayPriorityQueue queue = new HolidayPriorityQueue(year);

		for (int i = user.getNumberOfHolidays(); i > 0 && !queue.isEmpty();) {
			HolidayPriorityWrapper wrappedElement = queue.poll();
			if (wrappedElement.getDuration() > 1) {
				for (int dayIndex = 1; dayIndex <= wrappedElement.getDuration()
						&& i > 0; dayIndex++) {
					DateTime date = wrappedElement.getFirst().getDate();
					UserHoliday userHoliday = new UserHoliday("Vactionday",
							date.plusDays(dayIndex));
					user.getHolidays().add(userHoliday);
					i = i - 1;
				}
			}
		}

		return user.getHolidays();
	}

	private class HolidayPriorityQueue extends
			PriorityQueue<HolidayPriorityWrapper> {
		public HolidayPriorityQueue(CalendarYear year) {
			super(new Comparator<HolidayPriorityWrapper>() {
				@Override
				public int compare(HolidayPriorityWrapper o1,
						HolidayPriorityWrapper o2) {
					return o1.getDuration() - o2.getDuration();
				}
			});
			for (int i = 0; i + 1 < year.getAllHolidays().size(); i++) {
				HolidayPriorityWrapper wrapper = new HolidayPriorityWrapper(
						year.getAllHolidays().get(i), year.getAllHolidays()
								.get(i + 1));
				add(wrapper);
			}
		}

		public void print() {
			for (HolidayPriorityWrapper h : this) {
				System.out.println(h.getFirst() + " ------------------>  "
						+ h.getSecond());
			}
			System.out.println();
		}
	}

	private static class HolidayPriorityWrapper {
		private int duration = 0;
		private Holiday first;
		private Holiday second;

		public int getDuration() {
			return duration;
		}

		public Holiday getFirst() {
			return first;
		}

		public Holiday getSecond() {
			return second;
		}

		public HolidayPriorityWrapper(Holiday h1, Holiday h2) {
			this.duration = (int) new Duration(h1.getDate(), h2.getDate())
					.getStandardDays();
			this.first = h1;
			this.second = h2;
		}
	}
}
