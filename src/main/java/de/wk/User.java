package de.wk;

import java.util.ArrayList;

import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Holidays;

public class User {
	private String name;
	private int numberOfHolidays;
	private Holidays holidays;

	public User(String name, int numberOfHolidays) {
		this.name = name;
		this.numberOfHolidays = numberOfHolidays;
		this.holidays = new Holidays();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumberOfHolidays() {
		return this.numberOfHolidays;
	}

	public void setNumberOfHolidays(int numberOfHolidays) {
		this.numberOfHolidays = numberOfHolidays;
	}

	public ArrayList<Holiday<?>> getHolidayList() {
		return holidays;
	}
	
	public void setHolidayList(Holidays holidays){
		this.holidays = holidays;
	}
}
