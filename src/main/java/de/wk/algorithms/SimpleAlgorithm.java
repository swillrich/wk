package de.wk.algorithms;

import de.wk.User;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Holidays;
import de.wk.util.PriorityQueue;

public class SimpleAlgorithm implements Algorithm {
	
	private final int NUMBER_OF_PRIORITIES = 3;
	private PriorityQueue<Holiday<?>> holidaysByProiority;

	@Override
	public void calculate(CalendarYear year, User user) {
		this.holidaysByProiority = new PriorityQueue<Holiday<?>>(NUMBER_OF_PRIORITIES);
		Holidays holidays = year.getAllHolidays();
		for(Holiday<?> holiday : holidays){
			if(holiday.getDate().getDayOfWeek() == 6 ||
			   holiday.getDate().getDayOfWeek() == 7){
				this.holidaysByProiority.addElementWithPriority(0, holiday);
			}
			else if (holiday.getDate().getDayOfWeek() == 1 || 
					 holiday.getDate().getDayOfWeek() == 5) {
				this.holidaysByProiority.addElementWithPriority(1, holiday);
			}
			else {
				this.holidaysByProiority.addElementWithPriority(2, holiday);
			}
		}
		for (Holiday<?> holiday : this.holidaysByProiority.getAllElementsByPriority(2)) {
			System.out.println(holiday);
		}
	}

}
