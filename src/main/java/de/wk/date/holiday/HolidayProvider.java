package de.wk.date.holiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.immutable.BussUndBettag;
import de.wk.date.holiday.immutable.ChristiHimmelfahrt;
import de.wk.date.holiday.immutable.FixHoliday;
import de.wk.date.holiday.immutable.Karfreitag;
import de.wk.date.holiday.immutable.Ostermontag;
import de.wk.date.holiday.immutable.Ostersonntag;
import de.wk.date.holiday.immutable.Pfingstmontag;

/**
 * This class provides all holidays. <br>
 * In order to provide holidays depending on specific states the state must be
 * given. Without giving the state (e.g. pass null) no state is assumed and only
 * common holidays will provided.
 */
public class HolidayProvider {

	/**
	 * The state enumeration encompasses all states
	 */
	public static enum State {
		BW, BY, BE, BB, HB, HH, HE, MV, NI, NW, RP, SL, SN, ST, SH, TH;
	}

	/**
	 * This method provides all holidays being located between the given
	 * interval.
	 * 
	 * @param interval
	 *            The interval, in which the holidays are located
	 * @param state
	 *            The state on which the holidays should based on
	 * @return A Days (which is a collection) with the holidays being located
	 *         within the given interval
	 */
	public static Days provideBy(Interval interval, State state) {
		Days days = new Days();
		DateTime start = interval.getStart();
		DateTime end = interval.getEnd();
		for (int year = start.getYear(); year <= end.getYear(); year++) {
			initializeByYear(days, year, state);
		}
		Iterator<WKDateTime> iterator = days.iterator();
		while (iterator.hasNext()) {
			WKDateTime next = iterator.next();
			if (!interval.contains(next)) {
				iterator.remove();
			}
		}
		Collections.sort(days);
		return days;
	}

	/**
	 * This method provides all holidays being located between the given year.
	 * 
	 * @param year
	 *            The year, in which the holidays are located
	 * @param state
	 *            The state on which the holidays should based on
	 * @return A Days (which is a collection) with the holidays being located
	 *         within the given year
	 */
	public static Days provideBy(int year, State state) {
		WKDateTime wkDateTime = new WKDateTime(year, 1, 1);
		return provideBy(new Interval(wkDateTime, wkDateTime.getJodaDateTime().plusYears(1).minusDays(1)), state);
	}

	/**
	 * Determines all (official) holidays for the given year.
	 * 
	 * @param days
	 *            The Days (as collection) which is filled.
	 * @param state
	 *            The state on which the holidays are based on
	 */
	private static void initializeByYear(Days days, int year, State state) {
		FixHoliday neujahr = new FixHoliday("Neujahr", year, 1, 1);
		FixHoliday firstCMD = new FixHoliday("Erster Weihnachtstag", year, 12, 25);
		FixHoliday secondCMD = new FixHoliday("Zweiter Weihnachtstag", year, 12, 26);
		FixHoliday maifeiertag = new FixHoliday("Maifeiertag", year, 5, 1);
		FixHoliday tdde = new FixHoliday("Tag der Deutschen Einheit", year, 10, 3);
		BussUndBettag bussUndBettag = new BussUndBettag("Buss und Bettag", firstCMD);
		Ostersonntag ostersonntag = new Ostersonntag(year);
		Karfreitag karfreitag = new Karfreitag(ostersonntag);
		Ostermontag ostermontag = new Ostermontag(ostersonntag);
		ChristiHimmelfahrt christiHimmelfahrt = new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag);
		Pfingstmontag pfingstmontag = new Pfingstmontag(ostersonntag);

		// TODO: Add missing holidays
		ArrayList<WKDateTime> commonHolidays = new ArrayList<WKDateTime>();
		commonHolidays.add(neujahr);
		commonHolidays.add(firstCMD);
		commonHolidays.add(secondCMD);
		commonHolidays.add(maifeiertag);
		commonHolidays.add(tdde);
		commonHolidays.add(karfreitag);
		commonHolidays.add(ostersonntag);
		commonHolidays.add(ostermontag);
		commonHolidays.add(christiHimmelfahrt);
		commonHolidays.add(pfingstmontag);

		days.addAll(commonHolidays);
		if (state != null) {
			switch (state) {
			case BW:
			case BY:
				/* Heiligen drei Könige */
				/* Fronleichnam */
				/* Allerheiligen */
				break;
			case BB:
			case MV:
			case TH:
				/* Reformationstag */
				break;
			case NW:
			case RP:
				/* Fronleichnam */
				/* Allerheiligen */
				break;
			case HE:
				/* Fronleichnam */
				break;
			case SL:
				/* Fronleichnam */
				/* Mariä Himmelfahrt */
				/* Allerheiligen */
				break;
			case SN:
				/* Reformationstag */
				days.add(bussUndBettag);
				break;
			case ST:
				/* Reformationstag */
				/* Heiligen drei Könige */
				break;
			default:
				break;
			}
		}
	}
}
