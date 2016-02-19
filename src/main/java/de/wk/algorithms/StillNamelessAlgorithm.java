package de.wk.algorithms;

import java.util.ArrayList;
import java.util.List;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.WKInterval;
import de.wk.user.User;

public class StillNamelessAlgorithm implements HolidayCalculatorAlgorithm {

	private List<Gap> gaps = new ArrayList<Gap>();
	private List<WKInterval> free = new ArrayList<WKInterval>();

	@Override
	public void calculate(User user) throws Exception {
		initializeIntervals(user.getDays());

		System.out.println(user.getDays().toString());

		for (Gap gap : gaps) {
			WKInterval interval = gap.getPrevInterval();
			if (interval != null) {
				System.out.println("FRE: " + interval.getDaysBetween().get(0).toString());
			}
			System.out.println("GAP: " + gap.getDaysBetween().get(0).toString());
			interval = gap.getNextInterval();
			if (interval != null) {
				System.out.println("FRE: " + interval.getDaysBetween().get(0).toString());
			}
		}
	}

	private void initializeIntervals(Days days) throws Exception {
		WKDateTime start = null;
		boolean isWorkDay = false;
		for (int i = 0; i < days.size(); i++) {
			WKDateTime dateTime = days.get(i);
			isWorkDay = i + 1 < days.size() ? days.determineKindOf(days.get(i + 1).getDateTime()) == KindOfDay.WORKDAY
					: !isWorkDay;
			if (start == null) {
				start = dateTime;
			}
			if (isWorkDay != (days.determineKindOf(start.getDateTime()) == KindOfDay.WORKDAY)) {
				dateTime = days.get(i);
				if (!isWorkDay) {
					Gap gap = new Gap(start, dateTime);
					gaps.add(gap);
					if (free.size() > 0) {
						gap.setPrevInterval(free.get(free.size() - 1));
					}
				} else {
					WKInterval wkInterval = new WKInterval(start, dateTime);
					if (gaps.size() > 0) {
						gaps.get(gaps.size() - 1).setNextInterval(wkInterval);
					}
					free.add(wkInterval);
				}
				start = null;
			}
		}

	}

}
