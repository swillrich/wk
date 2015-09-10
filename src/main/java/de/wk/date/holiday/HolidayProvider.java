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
import de.wk.date.holiday.immutable.Fronleichnam;
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
	 * The state enumeration encompasses all states <br/>
	 * <br/>
	 * 
	 * <ul>
	 * <li>BW Baden-Württemberg</li>
	 * <li>BY Bayern</li>
	 * <li>BE Berlin</li>
	 * <li>BB Brandenburg</li>
	 * <li>HB Bremen</li>
	 * <li>HH Hamburg</li>
	 * <li>HE Hessen</li>
	 * <li>MV Mecklenburg-Vorpommern</li>
	 * <li>NI Niedersachsen</li>
	 * <li>NW Nordrhein-Westfalen</li>
	 * <li>RP Rheinland-Pfalz</li>
	 * <li>SL Saarland</li>
	 * <li>SN Sachsen</li>
	 * <li>ST Sachsen-Anhalt</li>
	 * <li>SH Schleswig-Holstein</li>
	 * <li>TH Thüringen</li>
	 * </ul>
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
		FixHoliday heiligeDreiKoenige = new FixHoliday("Heilige drei Könige", year, 1, 6);
		FixHoliday firstCMD = new FixHoliday("Erster Weihnachtstag", year, 12, 25);
		FixHoliday secondCMD = new FixHoliday("Zweiter Weihnachtstag", year, 12, 26);
		FixHoliday maifeiertag = new FixHoliday("Maifeiertag", year, 5, 1);
		FixHoliday tdde = new FixHoliday("Tag der Deutschen Einheit", year, 10, 3);
		BussUndBettag bussUndBettag = new BussUndBettag("Buss und Bettag", firstCMD);
		Ostersonntag ostersonntag = new Ostersonntag(year);
		Karfreitag karfreitag = new Karfreitag(ostersonntag);
		Ostermontag osterMontag = new Ostermontag(ostersonntag);
		ChristiHimmelfahrt christiHimmelfahrt = new ChristiHimmelfahrt("Christi Himmelfahrt", ostersonntag);
		Pfingstmontag pfingstmontag = new Pfingstmontag(ostersonntag);
		Fronleichnam fronleichnam = new Fronleichnam(ostersonntag);
		FixHoliday mariaeHimmelfahrt = new FixHoliday("Mariä Himmelfahrt", year, 8, 15);
		FixHoliday reformationsTag = new FixHoliday("Reformationstag", year, 10, 31);
		FixHoliday allerHeiligen = new FixHoliday("Allerheiligen", year, 11, 1);

		// TODO: Add missing holidays
		ArrayList<WKDateTime> commonHolidays = new ArrayList<WKDateTime>();
		commonHolidays.add(neujahr);
		commonHolidays.add(karfreitag);
		commonHolidays.add(osterMontag);
		commonHolidays.add(maifeiertag);
		commonHolidays.add(christiHimmelfahrt);
		commonHolidays.add(pfingstmontag);
		commonHolidays.add(tdde);
		commonHolidays.add(firstCMD);
		commonHolidays.add(secondCMD);

		days.addAll(commonHolidays);
		if (state != null) {
			switch (state) {
			case BW:
			case BY:
				commonHolidays.add(heiligeDreiKoenige);
				commonHolidays.add(fronleichnam);
				commonHolidays.add(allerHeiligen);
				break;
			case BE:
			case BB:
				commonHolidays.add(reformationsTag);
			case HB:
			case HH:
			case HE:
				commonHolidays.add(fronleichnam);
				break;
			case MV:
				commonHolidays.add(reformationsTag);
				break;
			case NI:
			case NW:
				commonHolidays.add(fronleichnam);
				commonHolidays.add(allerHeiligen);
			case RP:
				commonHolidays.add(fronleichnam);
				commonHolidays.add(allerHeiligen);
				break;
			case SL:
				commonHolidays.add(fronleichnam);
				commonHolidays.add(mariaeHimmelfahrt);
				commonHolidays.add(allerHeiligen);
				break;
			case SN:
				days.add(bussUndBettag);
				commonHolidays.add(reformationsTag);
				break;
			case ST:
				commonHolidays.add(heiligeDreiKoenige);
				commonHolidays.add(reformationsTag);
				break;
			case SH:
			case TH:
				commonHolidays.add(reformationsTag);
				break;
			}
		}
	}
}
