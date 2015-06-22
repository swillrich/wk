package de.wk.holiday;

import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Holidays;

public class CalendarYear {

	private Holidays holidays;
	
	public CalendarYear(int year) {
		this.holidays = new Holidays(year);
	}
	
	public void getHolidayByMonth(Month month){
		for(Holiday<?> h : this.holidays){
			if(h.getDate().getMonthOfYear() == Month.numberOfMonth(month)){
				System.out.println(h);
			}
		}
	}
	
	public Holidays getAllHolidays(){
		return this.holidays;
	}

	public void printYear() {
		StringBuilder formatBuilder = new StringBuilder();
		for (int i = 0; i < Month.values().length; i++) {
			formatBuilder.append("%-10s");
		}
		String format = String.format(formatBuilder.toString(), (Object[])Month.values());

		String underline = "";
		for (int i = 0; i < format.length(); i++) {
			underline += "=";
		}

		System.out.println(format + "\n" + underline);

		for (int i = 1; i <= 31; i++) {
			String[] days = new String[Month.values().length];
			for (int currentMonthIndex = 0; currentMonthIndex < days.length; currentMonthIndex++) {
				if (Month.values()[currentMonthIndex].getDays() >= i) {
					days[currentMonthIndex] = String.valueOf(i);
				} else {
					days[currentMonthIndex] = "-";
				}
			}
			System.out.println(String.format(formatBuilder.toString(), (Object[])days));
		}
	}
}
