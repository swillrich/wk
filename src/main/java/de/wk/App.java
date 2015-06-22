package de.wk;

import de.wk.holiday.CalendarYear;
import de.wk.holiday.Month;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CalendarYear year2015 = new CalendarYear(2015);
        year2015.getHolidayByMonth(Month.December);
        System.out.println("#####################");
        System.out.println(year2015.getAllHolidays());
    }
}
