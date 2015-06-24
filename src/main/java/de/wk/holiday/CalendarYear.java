package de.wk.holiday;

import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Holidays;

public class CalendarYear {

	private Holidays holidays;

	public CalendarYear(int year) {
		this.holidays = new Holidays().initializeByYear(year);
	}

	public void getHolidayByMonth(Month month) {
		for (Holiday<?> h : this.holidays) {
			if (h.getDate().getMonthOfYear() == Month.numberOfMonth(month)) {
				System.out.println(h);
			}
		}
	}

	public Holidays getAllHolidays() {
		return this.holidays;
	}
}
