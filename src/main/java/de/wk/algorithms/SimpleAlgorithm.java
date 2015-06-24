package de.wk.algorithms;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import de.wk.User;
import de.wk.holiday.CalendarYear;
import de.wk.holiday.Month;
import de.wk.holiday.day.FixHoliday;
import de.wk.holiday.day.Holiday;
import de.wk.holiday.day.Holidays;
import de.wk.util.PriorityQueue;

public class SimpleAlgorithm implements Algorithm {
	
	private final int NUMBER_OF_PRIORITIES = 3;
	private PriorityQueue<Holiday<?>> holidaysByProiority;
	private CalendarYear year;
	private User user;
	private Holidays holidays;
	private int numberOfHolidays;

	@Override
	public Holidays calculate(CalendarYear year, User user) {
		init(year, user);
		if(this.numberOfHolidays > 0) {
			prioritizeHolidays();
			fillGaps();
		}
		return this.holidays;
	}
	
	private void fillGaps() {
		// start with highest priority 2
		for (int i = 2; i >= 0; i--) {
			ArrayList<Holiday<?>> currentPriority = this.holidaysByProiority.getAllElementsByPriority(i);
			for (Holiday<?> currentHoliday : currentPriority) {
				if(this.numberOfHolidays == 0) {
					break;
				}
				switch (currentHoliday.getDate().getDayOfWeek()) {
				case 1:
				case 4:
				case 7:
					addNewHoliday(currentHoliday, true);
					break;
				case 2:
				case 5:
				case 6:
					addNewHoliday(currentHoliday, false);
					break;
				case 3:
					if(this.numberOfHolidays >= 2){
						Holiday<?> tmp = addNewHoliday(currentHoliday, true);
						addNewHoliday(tmp, true);						
					}
					break;
				default:
					break;
				}
			}
		}
		System.out.println("Used all days.");
	}
	
	private Holiday<?> addNewHoliday(Holiday<?> currentHoliday, boolean isPlusDay){
		int x = isPlusDay ? 1 : -1;
		DateTime tmp = currentHoliday.getDate().minusDays(x);
		FixHoliday newHoliday = new FixHoliday("Urlaubstag",
                                               tmp.getYear(),
                                               Month.getMonthByInt(tmp.getMonthOfYear()),
                                               tmp.getDayOfMonth());
		this.holidays.add(newHoliday);
		this.numberOfHolidays--;
		System.out.println("Added " + DateTimeFormat.forPattern("dd. MMM yyyy").print(tmp));
		System.out.println(" - " + this.numberOfHolidays + " days left.");
		return newHoliday;
	}
	
	private void prioritizeHolidays() {
		Holidays holidays = this.year.getAllHolidays();
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
	}
	
	private void init(CalendarYear year, User user) {
		this.holidaysByProiority = new PriorityQueue<Holiday<?>>(NUMBER_OF_PRIORITIES);
		this.year = year;
		this.user = user;
		this.numberOfHolidays = this.user.getNumberOfHolidays();
		this.holidays = new Holidays();
	}
}
